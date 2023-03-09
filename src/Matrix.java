public class Matrix {
    int elements[] = {};

    public Matrix(int elements[]) {
        this.elements = elements;
    }

    public void printMatrix() {
        for (int i = 0; i < this.elements.length; i++) {
            System.out.print(this.elements[i] + " ");
            if (i % 3 == 2)
                System.out.println("");
        }
    }

    public void sum(Matrix m2) {
        Matrix result = new Matrix(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        for (int i = 0; i < this.elements.length; i++) {
            result.elements[i] = this.elements[i] + m2.elements[i];
        }
        result.printMatrix();
    }

    public int[] multiply(Matrix matrix2) {
        int[] result = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += this.elements[i * 3 + k] * matrix2.elements[k * 3 + j];
                }
                result[i * 3 + j] = sum;
            }
        }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(result[i * 3 + j] + " ");
                }
                System.out.println();
            }

        return result;

    }

}
