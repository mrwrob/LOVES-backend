package jajarowi.loves;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.assignee.service.AssigneeService;
import jajarowi.loves.assignment.entity.Assignment;
import jajarowi.loves.assignment.service.AssignmentService;
import jajarowi.loves.label.entity.Label;
import jajarowi.loves.label.service.LabelService;
import jajarowi.loves.project.entity.Project;
import jajarowi.loves.project.service.ProjectService;
import jajarowi.loves.scientist.entity.Scientist;
import jajarowi.loves.scientist.service.ScientistService;
import jajarowi.loves.user.service.UserService;
import jajarowi.loves.video.entity.Video;
import jajarowi.loves.video.service.VideoService;
import jajarowi.loves.videoSet.entity.VideoSet;
import jajarowi.loves.videoSet.service.VideoSetService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class Initializer {

    private final AssigneeService assigneeService;
    private final ScientistService scientistService;
    //private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;
    private final AssignmentService assignmentService;
    private final VideoService videoService;
    private final VideoSetService videoSetService;
    private final LabelService labelService;
    private final UserService userService;

    public Initializer(AssigneeService assigneeService, ScientistService scientistService, ProjectService projectService, AssignmentService assignmentService, VideoService videoService, VideoSetService videoSetService, LabelService labelService, UserService userService) {
        this.assigneeService = assigneeService;
        this.scientistService = scientistService;
        //this.passwordEncoder = passwordEncoder;
        this.projectService = projectService;
        this.assignmentService = assignmentService;
        this.videoService = videoService;
        this.videoSetService = videoSetService;
        this.labelService = labelService;
        this.userService = userService;

    }

    private void createAssignee(String login) {
        assigneeService.create(Assignee.builder()
                .login(login)
                //.password(passwordEncoder.encode("alesuper"))
                .password("123")
                .assignmentList(new ArrayList<>())
                .build());
    }

    private void createScientist(String login) {
        scientistService.create(Scientist.builder()
                .login(login)
                //.password(passwordEncoder.encode("alesuper"))
                .password("123")
                .projectList(new ArrayList<>())
                .build());
    }

    private Project createProject(String scientist, String name) {
        if(scientistService.find(scientist).isPresent()) {
            return projectService.create(Project.builder()
                    .name(name)
                    .description("sample description")
                    .scientist(scientistService.find(scientist).get())
                    .build());
        }
        return null;
    }

    private void createAssignment(String assignee, String name, Long projectId, Long videoSetId) {
        if(assigneeService.find(assignee).isPresent() &&
                projectService.find(projectId).isPresent() &&
                videoSetService.find(videoSetId).isPresent()) {
            assignmentService.create(Assignment.builder()
                    .name(name)
                    .description("sample description")
                    .assignee(assigneeService.find(assignee).get())
                    .project(projectService.find(projectId).get())
                    .videoSet(videoSetService.find(videoSetId).get())
                    .build());
        }

    }

    private void createVideo(MultipartFile file, Long videoSetId) throws IOException {
        Optional<VideoSet> videoSet = videoSetService.find(videoSetId);
        if(videoSet.isPresent()){
            videoService.create(Video.builder()
                    .name(videoSetId + "_" + file.getOriginalFilename())
                    .videoSet(videoSet.get())
                    .data(file.getBytes())
                    .build());

            videoService.storeVideo(file, videoSetId);
        }
    }

    private VideoSet createVideoSet(String name, Long projectId) {
        if(projectService.find(projectId).isPresent()) {
            return videoSetService.create(VideoSet.builder()
                    .name(name)
                    .maxVideoCount(2)
                    .project(projectService.find(projectId).get())
                    .build());
        }
        return null;
    }

    private void createLabel(String name, String color, Long projectId, String type, String shortcut) {
        if(projectService.find(projectId).isPresent()) {
            labelService.create(Label.builder()
                    .name(name)
                    .color(color)
                    .project(projectService.find(projectId).get())
                    .type(type)
                    .shortcut(shortcut)
                    .build());
        }
    }

    private byte[] getBytes(String path) throws IOException {
        File file = new File(".\\test_files\\" + path);
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        int no_bytes_read = fileInputStream.read(bytes);
        fileInputStream.close();
        return bytes;
    }

    private MultipartFile getMultipartFile(byte[] bytes, String filename) {
        return new MockMultipartFile(filename, filename, "video/mp4", bytes);
    }

    @PostConstruct
    private synchronized void init() throws IOException {


        createAssignee("eryk");
        createAssignee("ernest");
        createAssignee("edward");
        createAssignee("euzebiusz");
        createAssignee("ekard");
        createAssignee("ewa");
        createAssignee("eryk2");
        createAssignee("ernest2");
        createAssignee("edward2");
        createAssignee("euzebiusz2");
        createAssignee("ekard2");
        createAssignee("ewa2");

        createScientist("natan");
        createScientist("nikodem");
        createScientist("natalia");
        createScientist("nikola");
        createScientist("nina");
        createScientist("norbert");

//        Project project1 = createProject("salwiszczur", "dzień w życiu małpy");
//        assert project1 != null;
//        Project project2 = createProject("salwiszczur", "dzień w życiu szczura");
//        assert project2 != null;
//        Project project3 = createProject("salwiszczur", "dzień w życiu słonia");
//
//        Project project4 = createProject("salwiszczur2", "dzień w życiu psa");
//        Project project5 = createProject("salwiszczur2", "dzień w życiu kota");
//        Project project6 = createProject("salwiszczur2", "dzień w życiu kotopsa");
//        assert project6 != null;
//
//        createProject("salwiszczur3", "dzień w życiu muchy");
//        createProject("salwiszczur3", "dzień w życiu pszczoły");
//        createProject("salwiszczur3", "dzień w życiu krzesła");
//
//
//
//        VideoSet videoSet1 = createVideoSet("pawian paweł 12.09.1941", project1.getId());
//        assert videoSet1 != null;
//        VideoSet videoSet2 = createVideoSet("orangutan oskar 31.02.2163", project2.getId());
//        assert videoSet2 != null;
//
//        VideoSet videoSet3 = createVideoSet("kotopies marek 12.09.1941", project6.getId());
//        assert videoSet3 != null;
//        VideoSet videoSet4 = createVideoSet("kotopies andrzej 31.02.2163", project6.getId());
//        assert videoSet4 != null;


//        String filename1 = "testFile1.mp4";
//        byte[] bytes1 = getBytes(filename1);
//        MultipartFile file1 = getMultipartFile(bytes1,filename1);
//        createVideo(file1, videoSet2.getId());
//
//        String filename2 = "testFile2.mp4";
//        byte[] bytes2 = getBytes(filename2);
//        MultipartFile file2 = getMultipartFile(bytes2,filename2);
//        createVideo(file2, videoSet4.getId());
//
//        String filename3 = "testFile3.mp4";
//        byte[] bytes3 = getBytes(filename3);
//        MultipartFile file3 = getMultipartFile(bytes3,filename3);
//        createVideo(file3, videoSet2.getId());
//
//        String filename4 = "10s.mp4";
//        byte[] bytes4 = getBytes(filename4);
//        MultipartFile file4 = getMultipartFile(bytes4,filename4);
//        createVideo(file4, videoSet1.getId());
//
//        String filename5 = "15s.mp4";
//        byte[] bytes5 = getBytes(filename5);
//        MultipartFile file5 = getMultipartFile(bytes5,filename5);
//        createVideo(file5, videoSet1.getId());

//        createAssignment("patol", "10-15", project1.getId(), videoSet1.getId());
//        createAssignment("patol", "arkanoid", project1.getId(), videoSet2.getId());
//        createAssignment("patol3", "małpa umiera", project1.getId(), videoSet1.getId());
//
//        createAssignment("patol2", "szczur skacze", project2.getId(), videoSet1.getId());
//        createAssignment("patol2", "szczur biega", project2.getId(), videoSet1.getId());
//        createAssignment("patol", "szczur umiera", project2.getId(), videoSet1.getId());
//
//        createAssignment("patol2", "kotopies skacze", project6.getId(), videoSet3.getId());
//        createAssignment("patol2", "kotopies biega", project6.getId(), videoSet3.getId());
//        createAssignment("patol", "kotopies umiera", project6.getId(), videoSet4.getId());
//
//        createLabel("Mrugnięcie", "#ff7300", project1.getId(), "point", "M");
//        createLabel("Kuc", "#3b29ff", project1.getId(), "range", "K");
//        createLabel("Bieg", "#ff2a00", project1.getId(), "range", "B");
//        createLabel("Huk", "#5fa378", project1.getId(), "point", "H");
//
//        createLabel("krzyk", "#7e2bb3", project6.getId(), "range", "K");
//        createLabel("płacz", "#00732e", project6.getId(), "range", "P");
//        createLabel("zgrzytanie zębów", "#000000", project6.getId(), "range", "Z");


    }
}
