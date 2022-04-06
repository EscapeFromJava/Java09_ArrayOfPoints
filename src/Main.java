import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.DoubleStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // читаем файл как список треугольников и выводим
        String input = "input.txt";
        ArrayList<Triangle> triangleList = getTriangles(input);
        System.out.println("Список треугольников");
        triangleList.forEach(x -> System.out.println(x.toString()));
        //сортируем по площади и выводим
        System.out.println("Список площадей треугольников до сортировки");
        triangleList.forEach(x -> System.out.println(x.getArea()));
        sortTriangleList(triangleList);
        System.out.println("Список площадей треугольников после сортировки");
        triangleList.forEach(x -> System.out.println(x.getArea()));
        //поиск треугольника с площадью близкой к заданной
        System.out.println("Список периметров треугольников");
        triangleList.forEach(x -> System.out.println(x.getPerimeter()));
        System.out.println("Треугольник с периметром, наиболее близким к X");
        System.out.println("Введите число \"X\":");
        System.out.println(getMinPerimeterTriangle(triangleList).toString());
        //поиск треугольника с вершиной близкой к точке 0.0
        System.out.println("Треугольник, вершина которого наиболее близка к точке 0,0:");
        minLengthToZero(triangleList);
    }

    public static ArrayList<Triangle> getTriangles(String input) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(input));
        ArrayList<Triangle> triangleList = new ArrayList<>();
        while (sc.hasNext()) {
            triangleList.add(new Triangle(new Point(sc.nextDouble(), sc.nextDouble()), new Point(sc.nextDouble(), sc.nextDouble()), new Point(sc.nextDouble(), sc.nextDouble())));
        }
        return triangleList;
    }

    public static void sortTriangleList(ArrayList<Triangle> triangleList) {
        triangleList.sort(Comparator.comparingDouble(Triangle::getArea));
    }

    public static Triangle getMinPerimeterTriangle(ArrayList<Triangle> triangleList) {
        Scanner sc = new Scanner(System.in);
        double temp = sc.nextInt();
        Triangle triangleWithMinPerimeter = triangleList.get(0);
        for (Triangle t : triangleList) {
            if (Math.abs(t.getPerimeter() - temp) < Math.abs(triangleWithMinPerimeter.getPerimeter() - temp))
                triangleWithMinPerimeter = t;
        }
        return triangleWithMinPerimeter;
    }

    public static void minLengthToZero(ArrayList<Triangle> triangleList) {
        double minLength = triangleList.stream().mapToDouble(Triangle::getMinLengthToZero).min().getAsDouble();
        for (Triangle t : triangleList) {
            if (t.getMinLengthToZero() == minLength)
                System.out.println(t);
        }
    }
}

class Triangle {
    Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public double getMinLengthToZero() {
        double d1 = Math.sqrt(Math.pow(p1.x, 2) + Math.pow(p1.y, 2));
        double d2 = Math.sqrt(Math.pow(p2.x, 2) + Math.pow(p2.y, 2));
        double d3 = Math.sqrt(Math.pow(p3.x, 2) + Math.pow(p3.y, 2));
        BigDecimal bg = new BigDecimal(Double.toString(DoubleStream.of(d1, d2, d3).min().getAsDouble()));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    public double getArea() {
        BigDecimal bg = new BigDecimal(Double.toString(0.5 * Math.abs((p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y))));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    public double getPerimeter() {
        double a1 = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
        double a2 = Math.sqrt(Math.pow((p2.x - p3.x), 2) + Math.pow((p2.y - p3.y), 2));
        double a3 = Math.sqrt(Math.pow((p3.x - p1.x), 2) + Math.pow((p3.y - p1.y), 2));
        BigDecimal bg = new BigDecimal(Double.toString(a1 + a2 + a3));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 +
                "perimeter=" + getPerimeter() + ", " +
                "area=" + getArea() + ", " +
                "minToZero=" + getMinLengthToZero() + "}";
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{x=" + x + ", y=" + y + "}";
    }
}
