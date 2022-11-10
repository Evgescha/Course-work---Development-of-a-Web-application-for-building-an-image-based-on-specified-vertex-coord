package com.hescha.linedrower.service;

import com.hescha.linedrower.model.Line;
import com.hescha.linedrower.model.Point;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    private static final int MULTIPLIER = 5;
    private static final int PADDING = 5;
    int size;

    public byte[] generateImage(List<Line> lines) throws IOException {
        defineIMageSize(lines);

        int width = size * 2 + PADDING;
        int height = size * 2 + PADDING;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);

        for (Line line : lines) {
            drawLine(g2d, line);
        }
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    private void defineIMageSize(List<Line> lines) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Line line : lines) {
            for (Point point : line.getPoints()) {
                int x = point.getPointX();
                int y = point.getPointY();

                if (minX > x) minX = x;
                if (minY > y) minY = y;
                if (maxX < x) maxX = x;
                if (maxY < y) maxY = y;
            }
        }

        int maxWidth = Integer.max(Math.abs(minX), maxX) * MULTIPLIER;
        int maxHeight = Integer.max(Math.abs(minY), maxY) * MULTIPLIER;
        size = Integer.max(maxWidth, maxHeight);
    }

    private void drawLine(Graphics2D g2d, Line line) {
        List<Point> points = line.getPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            Point a = points.get(i);
            Point b = points.get(i + 1);

            int x1 = a.getPointX();
            int y1 = a.getPointY();

            int x2 = b.getPointX();
            int y2 = b.getPointY();

            drawLine(g2d, x1, y1, x2, y2);
        }

    }

    private void drawLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(size + x1 * MULTIPLIER,
                size - y1 * MULTIPLIER,
                size + x2 * MULTIPLIER,
                size - y2 * MULTIPLIER);
    }
}
