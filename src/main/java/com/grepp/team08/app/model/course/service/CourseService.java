package com.grepp.team08.app.model.course.service;

import com.grepp.team08.app.model.course.dto.CourseDetailDto;
import com.grepp.team08.app.model.course.dto.MyCourseResponse;
import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.course.repository.RecommendCourseRepository;
import com.grepp.team08.app.model.course.repository.RegistMyCourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.place.dto.PlaceDetailDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.grepp.team08.app.model.course.repository.MyCourseRepository;
import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import com.grepp.team08.app.model.place.entity.Place;
import com.grepp.team08.app.model.place.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.io.File;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CourseService {

    private final MyCourseRepository myCourseRepository;
    private final PlaceRepository placeRepository;
    private final RegistMyCourseRepository courseRepository;
    private final RecommendCourseRepository recommendCourseRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        // src/main/resources/static/image 디렉토리 사용
        String projectRoot = System.getProperty("user.dir");
        this.uploadPath = projectRoot + File.separator + "src" + File.separator + "main" + 
                         File.separator + "resources" + File.separator + "static" + 
                         File.separator + "image";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        log.info("File upload path initialized to: {}", uploadPath);
    }

    public void saveCourse(MyDateCourseDto dto, Member member) {
        // Course 저장
        Course course = new Course();
        course.setTitle(dto.title());
        course.setDescription(dto.description());
        course.setId(member);
        myCourseRepository.save(course);

        // Place 저장
        for (PlaceSaveDto placeDto : dto.places()) {
            Place place = new Place();
            place.setCourseId(course);
            place.setPlaceName(placeDto.placeName());
            place.setAddress(placeDto.address());
            place.setPlaceUrl("");
            place.setLatitude(0);
            place.setLongitude(0);

            placeRepository.save(place);
        }
    }

    public Course getCourseById(Long courseId) {
        log.info("Finding course with ID: {}", courseId);
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> {
                log.error("Course not found with id: {}", courseId);
                return new EntityNotFoundException("Course not found with id: " + courseId);
            });
        log.info("Found course: {}", course);
        return course;
    }

    public List<Course> getCoursesByMember(Member member) {
        return courseRepository.findAllByIdOrderByCreatedAtDesc(member);
    }

    @Transactional
    public void registToRecommendCourse(Long courseId, List<String> imageUrls) {
        Course course = getCourseById(courseId);


        // Check if course is already registered as a recommend course
        if (recommendCourseRepository.existsByCourseId(course)) {
            throw new IllegalStateException("이미 추천 코스로 등록된 코스입니다.");
        }


        // 추천 코스 생성 및 저장
        RecommendCourse recommendCourse = new RecommendCourse();
        recommendCourse.setCourseId(course);
        recommendCourseRepository.save(recommendCourse);

        // 이미지 처리
        for (String imagePath : imageUrls) {
            Image image = new Image();
            image.setRecommendCourseId(recommendCourse); // EditorCourseId 대신 RecommendCourseId 설정
            image.setOriginFileName(extractFileName(imagePath));
            image.setRenameFileName(extractFileName(imagePath));
            image.setSavePath(imagePath);
            image.setType("image");
            imageRepository.save(image);
        }
    }

    @Transactional(readOnly = true)
    public List<MyCourseResponse> findMyCourses(Member member) {
        List<Course> courses = myCourseRepository.findById(member);
        return courses.stream()
            .map(course -> new MyCourseResponse(course.getCoursesId(), course.getTitle()))
            .collect(Collectors.toList());
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
    @Transactional(readOnly = true)
    public CourseDetailDto getCourseDetail(Long courseId) {
        Course course = getCourseById(courseId);

        CourseDetailDto dto = modelMapper.map(course, CourseDetailDto.class);

        List<Place> places = placeRepository.findByCourseId(course);
        List<PlaceDetailDto> placeDtos = places.stream()
                .map(place -> {
                    return new PlaceDetailDto(place.getPlaceName(), place.getAddress());
                })
                .toList();
        dto.setPlaces(placeDtos);

        List<Image> images = imageRepository.findByRecommendCourseId_CourseId(course);

        List<String> imageUrls = images.stream()
                .map(Image::getSavePath)
                .toList();
        dto.setImageUrl(imageUrls);

        return dto;
    }
}