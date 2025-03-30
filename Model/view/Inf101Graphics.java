package Model.view;

// University of Bergen INF101 helper methods for graphics with Swing.
// Methods for drawing images are adapted with permission from
// https://kosbie.net/cmu/spring-08/15-100/handouts/DrawImageFromFile.java
//
// You may use this code freely. It is provided as-is, without any warranty.

import javax.imageio.ImageIO;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class Inf101Graphics {

  //////////////////////////////////////
  /// String helper methods
  /////////////////////////////////////

  /**
   * Draw a string centered at the given point.
   *
   * @param g The graphics context to draw on
   * @param s The string to draw
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public static void drawCenteredString(Graphics g, String s, double x, double y) {
    FontMetrics metrics = g.getFontMetrics();
    double txtX = x - ((double) metrics.stringWidth(s))/2;
    double txtY = y - ((double) metrics.getHeight())/2 + metrics.getAscent();
    g.drawString(s, (int) Math.round(txtX), (int) Math.round(txtY));
  }

  /**
   * Draw a string centered at the given point.
   *
   * @param g The graphics context to draw on
   * @param s The string to draw
   * @param p The point on which to center the string
   */
  public static void drawCenteredString(Graphics g, String s, Point2D p) {
    Inf101Graphics.drawCenteredString(g, s, p.getX(), p.getY());
  }

  /**
   * Draw a string centered in the given rectangle. The string will be
   * vertically centered and horizontally centered with respect to the
   * given rectangle. If the string is too wide or to tall to fit in the
   * rectangle, it will overflow. If the width and the height are 0, the
   * string will be centered around the x and y coordinates.
   *
   * @param g The graphics context to draw on
   * @param s The string to draw
   * @param x The left edge of the rectangle
   * @param y The top edge of the rectangle
   * @param width The width of the rectangle
   * @param height The height of the rectangle
   */
  public static void drawCenteredString(Graphics g, String s, double x, double y,
                                        double width, double height) {
    drawCenteredString(g, s, x + width/2, y + height/2);
  }

  /**
   * Draw a string centered in the given shape. The string will be
   * vertically centered and horizontally centered with respect to the
   * bounding box of the shape. If the string is too wide or to tall to
   * fit within the bounds, it will overflow.
   *
   * @param g The graphics context to draw on
   * @param s The string to draw
   * @param shape in whose bounding box the string will be centered
   */
  public static void drawCenteredString(Graphics g, String s, Shape shape) {
    Inf101Graphics.drawCenteredString(g, s, shape.getBounds2D().getCenterX(),
        shape.getBounds2D().getCenterY());
  }

  //////////////////////////////////////
  /// Image helper methods
  /////////////////////////////////////

  /**
   * Draw the given image rotated and scaled with a top-left at x,y
   *
   * @param g The graphics context to draw on
   * @param image The image to draw
   * @param x The x coordinate of the top-left corner
   * @param y The y coordinate of the top-left corner
   * @param scale The scale factor (1.0 is 100%)
   * @param radians The angle to rotate the image in radians
   */
  public static void drawImage(Graphics g, Image image, double x, double y,
                               double scale, double radians) {
    double imageWidth  = image.getWidth(null);
    double imageHeight = image.getHeight(null);
    Dimension2D newSize = getImageSize(image, scale, radians);
    AffineTransform transform = new AffineTransform();
    transform.translate(x+newSize.getWidth()/2,y+newSize.getHeight()/2); // last (not first!)
    transform.rotate(radians);
    transform.scale(scale,scale);
    transform.translate(-imageWidth/2, -imageHeight/2);  // first
    ((Graphics2D)g).drawImage(image,transform,null);
  }

  /**
   * Draw the given image scaled with a top-left at x,y
   *
   * @param g The graphics context to draw on
   * @param image The image to draw
   * @param x The x coordinate of the top-left corner
   * @param y The y coordinate of the top-left corner
   * @param scale The scale factor (1.0 is 100%)
   */
  public static void drawImage(Graphics g, Image image, double x, double y,
                               double scale) {
    Inf101Graphics.drawImage(g, image, x, y, scale, 0);
  }

  /**
   * Draw the given image rotated and scaled with a center at x,y
   *
   * @param g The graphics context to draw on
   * @param image The image to draw
   * @param cx The x coordinate of the center
   * @param cy The y coordinate of the center
   * @param scale The scale factor (1.0 is 100%)
   * @param radians The angle to rotate the image in radians
   */
  public static void drawCenteredImage(Graphics g, Image image, double cx, double cy,
                                       double scale, double radians) {
    Dimension2D newSize = getImageSize(image, scale, radians);
    drawImage(g, image, cx - (newSize.getWidth())/2,
        cy - (newSize.getHeight())/2, scale, radians);
  }

  /**
   * Draw the given image scaled with a center at x,y
   *
   * @param g The graphics context to draw on
   * @param image The image to draw
   * @param cx The x coordinate of the center
   * @param cy The y coordinate of the center
   * @param scale The scale factor (1.0 is 100%)
   */
  public static void drawCenteredImage(Graphics g, Image image, double cx, double cy,
                                       double scale) {
    Inf101Graphics.drawCenteredImage(g, image, cx, cy, scale, 0);
  }

  /**
   * Get the size of the (bounding box of the) image after it has been
   * scaled and rotated
   *
   * @param image The image
   * @param scale The scale factor (1.0 is 100%)
   * @param radians The angle to rotate the image in radians
   * @return The size of the (bounding box of) the image after it has been
   *          scaled and rotated
   */
  public static Dimension2D getImageSize(Image image, double scale, double radians) {
    double imageWidth  = image.getWidth(null);
    double imageHeight = image.getHeight(null);
    AffineTransform transform = new AffineTransform();
    transform.rotate(radians);
    transform.scale(scale,scale);
    transform.translate(-imageWidth/2, -imageHeight/2);  // first
    double[] x = { 0, imageWidth, imageWidth ,     0       };
    double[] y = { 0,     0     , imageHeight, imageHeight };
    double minx=0, maxx=0, miny=0, maxy=0;
    Point2D.Double src = new Point2D.Double();
    Point2D.Double dst = new Point2D.Double();
    for (int i=0; i<4; i++) {
      src.setLocation(x[i],y[i]);
      transform.transform(src,dst);
      if (i == 0) {
        minx = maxx = dst.getX();
        miny = maxy = dst.getY();
      }
      else {
        minx = Math.min(dst.getX(),minx);
        miny = Math.min(dst.getY(),miny);
        maxx = Math.max(dst.getX(),maxx);
        maxy = Math.max(dst.getY(),maxy);
      }
    }
    final double width = maxx - minx;
    final double height = maxy - miny;
    return new Dimension2D() {
      @Override
      public double getWidth() {
        return width;
      }

      @Override
      public double getHeight() {
        return height;
      }

      @Override
      public void setSize(double width, double height) {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * Converts an image file into a BufferedImage. Note that in a maven
   * project, the requested image file must be in the src/main/resources
   * folder. It accepts most common image formats, such as jpg, png, etc.
   *
   * @param filename The name of the image file
   * @return The image as a BufferedImage
   * @throws RuntimeException if the image file is not found
   */
  public static BufferedImage loadImageFromResources(String filename) {
    if (!filename.startsWith("/")) {
      filename = "/" + filename;
    }
    InputStream is = Inf101Graphics.class.getResourceAsStream(filename);
    if (is == null) {
      throw new RuntimeException("Could not find image file in resources: " + filename);
    }
    try {
      return ImageIO.read(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Converts an image file into a BufferedImage.
   *
   * @param file A file object with location the image file
   * @return The image as a BufferedImage, or null if not found or not an image
   */
  public static BufferedImage loadImageFromFile(File file) {
    try {
      return ImageIO.read(file);
    } catch (IOException | IllegalArgumentException e) {
      return null;
    }
  }
}
