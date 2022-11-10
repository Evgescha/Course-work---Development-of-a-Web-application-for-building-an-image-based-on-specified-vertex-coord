package com.hescha.linedrower.controller;

import com.hescha.linedrower.model.Line;
import com.hescha.linedrower.model.Lines;
import com.hescha.linedrower.model.Point;
import com.hescha.linedrower.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final ImageService imageService;

    private final ApplicationContext context;
    public static int counter = 0;
    private static Map<Integer, byte[]> map = new HashMap<>();

    @GetMapping
    public String getIndex(Model model) {
        Point pointA = new Point(1, 5, 6);
        Point pointB = new Point(2, 7, 8);
        Line line = new Line(1, List.of(pointA, pointB));
        model.addAttribute("lines", List.of(line));
        return "index.html";
    }

    @PostMapping("/image")
    public ResponseEntity<String> makeImage(@RequestBody Lines lines) throws IOException {
        counter++;
        map.put(counter, imageService.generateImage(lines.getLines()));
        return ResponseEntity.ok("/image/" + counter);
    }

    @GetMapping(path = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getResult(@PathVariable Integer id) throws IOException {
        if (map.containsKey(id)) {
            return map.get(id);
        }

        InputStream in = context.getResource("classpath:/static/404.png").getInputStream();
        return IOUtils.toByteArray(in);
    }
}
