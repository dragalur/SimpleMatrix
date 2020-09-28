import java.util.ArrayList;

public class Mathem {
	//Функция убирания строки и столбца из матрицы для вычисления определителя
	private  static double[][] getMatrixWithoutRowAndCol(double[][] Matrix, int rows, int cols) {
        int offsetRows, offsetCols;
        double[][] arr = new double[Matrix.length - 1][Matrix.length - 1];
        for (int i = 0; i < Matrix.length - 1; i++) {
            offsetRows = 0;
            if (i >= rows) {
                offsetRows = 1;
            }
            for (int j = 0; j < Matrix.length - 1; j++) {
                offsetCols = 0;
                if (j >= cols) {
                    offsetCols = 1;
                }
                arr[i][j] = Matrix[i + offsetRows][j + offsetCols];
            }
        }
        return arr;
    }
	//Функция вычисления определителя
    static double detMatrix(double[][] Matrix, int size) {
        double det = 0, degree = 1;
        if (size == 1)
            return Matrix[0][0];
        else if (size == 2) {
            return Matrix[0][0] * Matrix[1][1] - Matrix[0][1] * Matrix[1][0];
        } else {
            for (int j = 0; j < size; j++) {
                double arr[][] = getMatrixWithoutRowAndCol(Matrix, 0, 3);
                det = det + (degree * Matrix[0][j] * detMatrix(arr, size - 1));
                degree = -degree;
            }

        }
        return det;
    }
    //Sum array
    static double sumMatrix(double [][]Matrix,int rows,int cols) {
    	double sum=0;
    	for(int i=0;i<rows;i++) {
    		for(int j=0;j<cols;j++) {
    			sum+=Matrix[i][j];
    		}
    	}
    	return sum;
    }
    //Min elem
    static String minElemArray(double[][] Matrix,int rows,int cols) {
    	double min=100;
    	String ret;
    	int indI=0,indJ=0;
    	for(int i=0;i<rows;i++) {
    		for(int j=0;j<cols;j++) {
    			if(Matrix[i][j]<min) {
    				min=Matrix[i][j];
    				indI=i+1;
    				indJ=j+1;
    			}
    		}
    	}
    	ret="Минимальный элемент матрицы в строке ("+indI+"),столбце ("+indJ+") : "+min;
    	return ret;
    }
    
    static String maxElemArray(double[][] Matrix,int rows,int cols) {
    	double max=-100;
    	String ret;
    	int indI=0,indJ=0;
    	for(int i=0;i<rows;i++) {
    		for(int j=0;j<cols;j++) {
    			if(Matrix[i][j]>max) {
    				max=Matrix[i][j];
    				indI=i+1;
    				indJ=j+1;
    			}
    		}
    	}
    	ret="Максимальный элемент матрицы в строке ("+indI+"),столбце ("+indJ+") : "+max;
    	return ret;
    }
    
    static String[] elemWithZezo(double Matrix[][],int rows,int cols) {
    	ArrayList<String> list= new ArrayList<String>();
    	for(int i=0;i<rows;i++) {
    		for(int j=0;j<cols;j++) {
    			if(Matrix[i][j]==0) {
    				list.add("В строке("+(i+1)+"), столбце ("+(j+1)+") ноль");
    			}
    		}    		
    }
    String arr[] =new String [list.size()];
    		list.toArray(arr);
    		return arr;   
}
}
