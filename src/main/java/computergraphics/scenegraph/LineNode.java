package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;


public class LineNode extends LeafNode {



    Vector v1;
    Vector v2;
    VertexBufferObject vbo;

    public LineNode(Vector v1, Vector v2){
        this.v1 = v1;
        this.v2 = v2;

        vbo =  new VertexBufferObject();
        createVbo();
    }

    public void setVecs(Vector v1, Vector v2) {
        this.v1 = v1;
        this.v2 = v2;
        vbo.invalidate();
        createVbo();
    }

    public void createVbo(){
        System.out.println("building vbo");
        List<RenderVertex> renderVertices = new ArrayList<>();

        renderVertices.add(new RenderVertex(v1,new Vector(0,0,0),new Vector(1,0,0,1)));
        renderVertices.add(new RenderVertex(v2,new Vector(0,0,0),new Vector(1,0,0,1)));

        vbo.Setup(renderVertices,GL2.GL_LINES);

    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if(mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }

    }
}
