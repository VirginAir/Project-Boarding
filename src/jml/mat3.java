package jml;

/**
 *
 * @author Ben Cook
 */
public class mat3 {
    private float[][] matrix = {
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 1}
    };
    
    public mat3(){};

    public float[][] getMatrix() {
        return matrix;
    }
    
    public float[] getMatrixGLForm(){
        float[] r = {matrix[0][0], matrix[1][0], matrix[2][0], matrix[3][0],
                    matrix[0][1], matrix[1][1], matrix[2][1], matrix[3][1],
                    matrix[0][2], matrix[1][2], matrix[2][2], matrix[3][2],
                    matrix[0][3], matrix[1][3], matrix[2][3], matrix[3][3]};
        return r;
    }
    
    public void setIdentity(){
        this.matrix[0][0] = 1;
        this.matrix[0][1] = 0;
        this.matrix[0][2] = 0;
        this.matrix[0][3] = 0;
        
        this.matrix[1][0] = 0;
        this.matrix[1][1] = 1;
        this.matrix[1][2] = 0;
        this.matrix[1][3] = 0;
        
        this.matrix[2][0] = 0;
        this.matrix[2][1] = 0;
        this.matrix[2][2] = 1;
        this.matrix[2][3] = 0;
        
        this.matrix[3][0] = 0;
        this.matrix[3][1] = 0;
        this.matrix[3][2] = 0;
        this.matrix[3][3] = 1;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }
    
    public void translate(float x, float y){
        matrix[0][3] += x;
        matrix[1][3] += y;
    }
}
