public class ComplexMatrix {
    private final Complex[][] cells;
    private final int rowCount;
    private final int columnCount;

    public ComplexMatrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.cells = new Complex[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                cells[i][j] = new Complex(0, 0);
            }
        }
    }

    public int getRowCount() { return rowCount; }
    public int getColumnCount() { return columnCount; }

    public Complex getCell(int row, int column) { return cells[row][column]; }

    public void setCell(int row, int column, Complex value) { cells[row][column] = value; }

    public ComplexMatrix plus(ComplexMatrix other) {
        if (rowCount != other.rowCount || columnCount != other.columnCount) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        ComplexMatrix result = new ComplexMatrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result.cells[i][j] = cells[i][j].plus(other.cells[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix times(ComplexMatrix other) {
        if (columnCount != other.rowCount) {
            throw new IllegalArgumentException("Несовместимые размеры для умножения");
        }
        ComplexMatrix result = new ComplexMatrix(rowCount, other.columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < other.columnCount; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < columnCount; k++) {
                    sum = sum.plus(cells[i][k].times(other.cells[k][j]));
                }
                result.cells[i][j] = sum;
            }
        }
        return result;
    }

    public ComplexMatrix transposed() {
        ComplexMatrix result = new ComplexMatrix(columnCount, rowCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result.cells[j][i] = cells[i][j];
            }
        }
        return result;
    }

    public Complex determinant() {
        if (rowCount != columnCount) {
            throw new IllegalArgumentException("Определитель только для квадратных матриц");
        }
        return determinantOf(cells, rowCount);
    }

    private Complex determinantOf(Complex[][] matrix, int size) {
        if (size == 1) {
            return matrix[0][0];
        }
        if (size == 2) {
            return matrix[0][0].times(matrix[1][1])
                    .minus(matrix[0][1].times(matrix[1][0]));
        }
        Complex result = new Complex(0, 0);
        for (int column = 0; column < size; column++) {
            Complex[][] minor = minorOf(matrix, 0, column, size);
            Complex minorDet = determinantOf(minor, size - 1);
            Complex term = matrix[0][column].times(minorDet);
            if (column % 2 == 0) {
                result = result.plus(term);
            } else {
                result = result.minus(term);
            }
        }
        return result;
    }

    private Complex[][] minorOf(Complex[][] matrix, int skipRow, int skipColumn, int size) {
        Complex[][] minor = new Complex[size - 1][size - 1];
        int minorRow = 0;
        for (int i = 0; i < size; i++) {
            if (i == skipRow) continue;
            int minorColumn = 0;
            for (int j = 0; j < size; j++) {
                if (j == skipColumn) continue;
                minor[minorRow][minorColumn] = matrix[i][j];
                minorColumn++;
            }
            minorRow++;
        }
        return minor;
    }

    public ComplexMatrix inverse() {
        if (rowCount != columnCount) {
            throw new IllegalArgumentException("Обратная матрица только для квадратных матриц");
        }
        Complex det = determinant();
        if (det.isZero()) {
            throw new ArithmeticException("Матрица вырождена, обратной нет");
        }
        int size = rowCount;
        ComplexMatrix adjugate = new ComplexMatrix(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Complex[][] minor = minorOf(cells, i, j, size);
                Complex minorDet = determinantOf(minor, size - 1);
                if ((i + j) % 2 != 0) {
                    minorDet = new Complex(-minorDet.realPart(), -minorDet.imagPart());
                }
                adjugate.cells[j][i] = minorDet;
            }
        }
        ComplexMatrix result = new ComplexMatrix(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.cells[i][j] = adjugate.cells[i][j].dividedBy(det);
            }
        }
        return result;
    }

    public ComplexMatrix dividedBy(ComplexMatrix other) {
        return this.times(other.inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            sb.append("[ ");
            for (int j = 0; j < columnCount; j++) {
                sb.append(cells[i][j]);
                if (j < columnCount - 1) sb.append("  ");
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }
}
