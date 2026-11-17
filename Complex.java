public class Complex {
    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex plus(Complex other) {
        return new Complex(real + other.real, imag + other.imag);
    }

    public Complex minus(Complex other) {
        return new Complex(real - other.real, imag - other.imag);
    }

    public Complex times(Complex other) {
        return new Complex(
                real * other.real - imag * other.imag,
                real * other.imag + imag * other.real
        );
    }

    public Complex dividedBy(Complex other) {
        double denominator = other.real * other.real + other.imag * other.imag;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Complex(
                (real * other.real + imag * other.imag) / denominator,
                (imag * other.real - real * other.imag) / denominator
        );
    }

    public boolean isZero() {
        return real == 0 && imag == 0;
    }

    @Override
    public String toString() {
        if (imag == 0) return String.format("%.2f", real);
        if (real == 0) return String.format("%.2fi", imag);
        if (imag < 0) return String.format("%.2f - %.2fi", real, -imag);
        return String.format("%.2f + %.2fi", real, imag);
    }
}
