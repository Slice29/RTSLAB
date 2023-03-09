public class main {

    public static void main(String[] args) {
         ComplexNumber num1 = new ComplexNumber(2,5);
         ComplexNumber num2 = new ComplexNumber(4,-1);
         num1.showC();
         num2.showC();
        System.out.println("Complex number sumation");
         num1.sum(num2);
        System.out.println("Complex number multiplication");
         num1.product(num2);

         Matrix m1 = new Matrix(new int[] {2,3,1,7,1,6,9,2,4});
         Matrix m2 = new Matrix(new int[]{8,5,3,3,9,2,2,7,3});
        System.out.println("Matrix sum");
         m1.sum(m2);
        System.out.println("Matrix Multiplication");
         m1.multiply(m2);


    }

}
