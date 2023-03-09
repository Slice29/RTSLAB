 class ComplexNumber {

        int real, image;
        public ComplexNumber(int r, int i)
        {
            this.real = r;
            this.image = i;
        }

        public void showC()
        {
            System.out.println(this.real +  " + " + this.image + "i ");
        }

        public void sum(ComplexNumber num1)
        {
            int sumR = this.real + num1.real;
            int sumC = this.image + num1.image;
            System.out.println(sumR + " + " + sumC + "i");
        }
     public void product(ComplexNumber num1)
     {
         int prodR = this.real*num1.real - this.image*num1.image;
         int prodC = this.image * num1.real + this.real + num1.image;
         System.out.println(prodR + " + " + prodC + "i");
     }
    }
