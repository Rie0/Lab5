package org.lab5.lab5_project_tracker.Controller;

import org.lab5.lab5_project_tracker.ApiResponse.ApiResponse;
import org.lab5.lab5_project_tracker.Model.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/project_tracker")
public class ProjectController {
    ArrayList<Project> projects = new ArrayList<Project>();
    static int idCounter = 1; //to make ids incremental
    //---------------------Get---------------------
    @GetMapping("get/projects")
    public ApiResponse getProjects() {
        if (projects.isEmpty()) {
            return new ApiResponse("404", "No projects found");
        } else {
            return new ApiResponse("200", "Project list: " + projects);
        }
    }

    @GetMapping("get/projects/by_title/{title}")
    public ApiResponse getProjectsByTitle(@PathVariable String title) {
        ArrayList<Project> foundProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getTitle()==null) {//prevents nullPointer exception
                continue;
            }
            if (project.getTitle().equals(title)) {
                foundProjects.add(project);
            }
        }
        if (foundProjects.isEmpty()) {
            return new ApiResponse("404", "No projects found");
        } else {
            return new ApiResponse("200", "Project found: " + foundProjects);
        }
    }

    @GetMapping("get/projects/by_company/{company_name}")
    public ApiResponse getProjectsByCompanyName(@PathVariable String company_name) {
        ArrayList<Project> foundProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompany_name()==null) {//prevents nullPointer exception (just in case)
                continue;
            }
            if (project.getCompany_name().equals(company_name)) {
                foundProjects.add(project);
            }
        }
        if (foundProjects.isEmpty()) {
            return new ApiResponse("404", "No projects found");
        }else {
            return new ApiResponse("200", "Projects from "+company_name+": " + foundProjects);
        }
    }

    //---------------------Post---------------------
    @PostMapping("post/project")
    public ApiResponse addProject(@RequestBody Project project) {
        if (project.getTitle()==null||project.getDescription()==null||project.getCompany_name()==null||project.getStatus()==null) {
            return new ApiResponse("400", "Bad Request, missing data");
        }
        if (!project.getStatus().equalsIgnoreCase("done")&&!project.getStatus().equalsIgnoreCase("not done")) {
            return new ApiResponse("400", "Bad Request, invalid data");
        }
        project.setId(idCounter++);
        projects.add(project);
        return new ApiResponse("200", "Project added");
    }
    //---------------------Put----------------------
    @PutMapping("/update/project/{id}")
    public ApiResponse updateProject(@PathVariable int id, @RequestBody Project project) {
        if (project.getTitle()==null||project.getDescription()==null||project.getCompany_name()==null||project.getStatus()==null) {
            return new ApiResponse("400", "Bad Request, missing data");
        }
        if (!project.getStatus().equalsIgnoreCase("done")&&!project.getStatus().equalsIgnoreCase("not done")) {
            return new ApiResponse("400", "Bad Request, invalid data");
        }
        for(Project p : projects) {
            if (p.getId()==id) {
                project.setId(id);
                projects.set(projects.indexOf(p), project);
                return new ApiResponse("200", "Project updated");
            }
        }
        return new ApiResponse("404", "No project found");
    }

    @PutMapping("update/project/status/{id}/{status}")
    public ApiResponse updateProjectStatus(@PathVariable int id, @PathVariable String status) {
        if (!status.equalsIgnoreCase("done")&&!status.equalsIgnoreCase("not done")) {
            return new ApiResponse("400", "Bad Request, invalid data");
        }
        for (Project p : projects) {
            if (p.getId()==id) {
                p.setStatus(status);
                return new ApiResponse("200", "Project status updated");
            }
        }
        return new ApiResponse("404", "No project found");
    }


    //---------------------Delete-------------------
    @DeleteMapping("/delete/project/{id}")
    public ApiResponse deleteProject(@PathVariable int id) {
        for(Project p : projects) {
            if (p.getId()==id) {
                projects.remove(p);
                return new ApiResponse("200", "Project deleted");
            }
        }
        return new ApiResponse("404", "No project found");

    }

}
