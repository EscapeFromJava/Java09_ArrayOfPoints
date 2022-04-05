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
        System.out.println("Треугольник с периметром, наиболее близким к X");
        System.out.println("Введите число \"X\":");
        System.out.println(getMinPerimeterTriangle(triangleList).toString());
        //поиск треугольника с вершиной близкой к точке 0.0
        System.out.println("Треугольник, вершина которого наиболее близка к точке 0,0:");
        minLengthToZero(triangleList);
    }

    private static void minLengthToZero(ArrayList<Triangle> triangleList) {
        double minLength = triangleList.stream().mapToDouble(Triangle::getMinLengthToZero).min().getAsDouble();
        for (Triangle t : triangleList) {
            if (t.getMinLengthToZero() == minLength)
                System.out.println(t);
        }
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

    public static void sortTriangleList(ArrayList<Triangle> triangleList) {
        triangleList.sort(Comparator.comparingDouble(Triangle::getArea));
    }

    public static ArrayList<Triangle> getTriangles(String input) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(input));
        ArrayList<Triangle> triangleList = new ArrayList<>();
        while (sc.hasNext()) {
            triangleList.add(new Triangle(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble()));
        }
        return triangleList;
    }
}

class Triangle {
    double x1, y1, x2, y2, x3, y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getMinLengthToZero() {
        double d1 = Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2));
        double d2 = Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2));
        double d3 = Math.sqrt(Math.pow(x3, 2) + Math.pow(y3, 2));
        BigDecimal bg = new BigDecimal(Double.toString(DoubleStream.of(d1, d2, d3).min().getAsDouble()));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    public double getArea() {
        BigDecimal bg = new BigDecimal(Double.toString(0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1))));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    public double getPerimeter() {
        double a1 = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double a2 = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));
        double a3 = Math.sqrt(Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2));
        BigDecimal bg = new BigDecimal(Double.toString(a1 + a2 + a3));
        return bg.setScale(2, RoundingMode.UP).doubleValue();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{x1=" + x1 + ", y1=" + y1 + ", " +
                "x2=" + x2 + ", y2=" + y2 + ", " +
                "x3=" + x3 + ", y3=" + y3 + ", " +
                "perimeter=" + getPerimeter() + ", " +
                "area=" + getArea() + ", " +
                "minToZero=" + getMinLengthToZero() + "}";
    }
}
