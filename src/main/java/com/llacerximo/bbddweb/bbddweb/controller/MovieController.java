package com.llacerximo.bbddweb.bbddweb.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.llacerximo.bbddweb.bbddweb.business.entity.Movie;
import com.llacerximo.bbddweb.bbddweb.business.service.ActorService;
import com.llacerximo.bbddweb.bbddweb.business.service.DirectorService;
import com.llacerximo.bbddweb.bbddweb.business.service.MovieService;
import com.llacerximo.bbddweb.bbddweb.business.service.impl.ActorServiceImpl;
import com.llacerximo.bbddweb.bbddweb.business.service.impl.DirectorServiceImpl;
import com.llacerximo.bbddweb.bbddweb.business.service.impl.MovieServiceImpl;
import com.llacerximo.bbddweb.bbddweb.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/movies")
public class MovieController {
    
    private MovieService movieService = new MovieServiceImpl();
    private DirectorService directorService = new DirectorServiceImpl();
    private ActorService actorService = new ActorServiceImpl();

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("movies", movieService.getAll());
        System.out.println("Lista de peliculas");
        return "movies";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable String id) throws SQLException, ResourceNotFoundException{
        try{
            model.addAttribute("movie", movieService.getById(id));
            System.out.println(movieService.getById(id).getActors());
            return "movie";
        } catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/insertMovie")
    public String insertFrom(Model model){
        model.addAttribute("directors", directorService.getAll());
        model.addAttribute("actors", actorService.getAll());    
        return "insertMovie";
    }

    @PostMapping("")
    public String insert(HttpServletRequest httpServletRequest) throws ResourceNotFoundException, SQLException{
        try {
            String id = httpServletRequest.getParameter("id");
            String title = httpServletRequest.getParameter("title");
            int year = Integer.parseInt(httpServletRequest.getParameter("year"));
            int runtime = Integer.parseInt(httpServletRequest.getParameter("runtime"));
            Movie movie = new Movie(id, title, year, runtime);

            String[] actorsArray = httpServletRequest.getParameterValues("actors");
            for (int i = 0; i < actorsArray.length; i++) {
                movie.setActor(actorService.getById(actorsArray[i]));
            }

            movie.setDirector(directorService.getById(httpServletRequest.getParameter("director")));

            movieService.insert(movie);
            return "redirect:/movies";
        } catch (ResourceNotFoundException e){
            System.out.println("No se ha encontrado el director o los actores");
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable String id) throws SQLException{
        try {
            movieService.delete(id);
            return "redirect:/movies";
        } catch (ResourceNotFoundException e){
            System.out.println("NO se ha encontrado la pelicula");
            return "error";
        } catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/updateMovie/{id}")
    public String updateForm(Model model, @PathVariable String id) throws SQLException, ResourceNotFoundException{
        model.addAttribute("directors", directorService.getAll());
        model.addAttribute("actors", actorService.getAll());
        model.addAttribute("movie", movieService.getById(id));
        return "updateMovie";
    }

    @PutMapping("/{id}")
    public String update(HttpServletRequest httpServletRequest, @PathVariable String id) throws ResourceNotFoundException, SQLException{
        try {
            String title = httpServletRequest.getParameter("title");
            int year = Integer.parseInt(httpServletRequest.getParameter("year"));
            int runtime = Integer.parseInt(httpServletRequest.getParameter("runtime"));
            Movie movie = new Movie(id, title, year, runtime);
            String[] actorsArray = httpServletRequest.getParameterValues("actors");
            for (int i = 0; i < actorsArray.length; i++) {
                movie.setActor(actorService.getById(actorsArray[i]));
            }
            movie.setDirector(directorService.getById(httpServletRequest.getParameter("director")));
//            System.out.println(movie);
            movieService.update(movie);
            return "redirect:/movies";
        } catch (ResourceNotFoundException e) {
            System.out.println("No se ha encontrado la pelicula");
            return "error";
        } catch (Exception e){
            return "error";
        }
        
    }
}
