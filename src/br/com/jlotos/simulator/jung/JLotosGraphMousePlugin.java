package br.com.jlotos.simulator.jung;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.apache.commons.collections15.Factory;

import br.com.jlotos.simulator.JLotosSimulator;
import br.com.jlotos.simulator.JLotosSimulator.JLotosFactory;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.util.ArrowFactory;

/**
 * A plugin that can create vertices, undirected edges, and directed edges
 * using mouse gestures.
 * 
 * @author Tom Nelson
 *
 */
public class JLotosGraphMousePlugin<V,E> extends AbstractGraphMousePlugin implements
    MouseListener, MouseMotionListener {
    
    protected V startVertex;
    protected Point2D down;
    
    protected CubicCurve2D rawEdge = new CubicCurve2D.Float();
    protected Shape edgeShape;
    protected Shape rawArrowShape;
    protected Shape arrowShape;
    protected VisualizationServer.Paintable edgePaintable;
    protected VisualizationServer.Paintable arrowPaintable;
    protected EdgeType edgeIsDirected;
    protected Factory<V> vertexFactory;
    protected Factory<E> edgeFactory;
    
    public JLotosGraphMousePlugin(Factory<V> vertexFactory, Factory<E> edgeFactory) {
        this(MouseEvent.BUTTON1_MASK, vertexFactory, edgeFactory);
    }

    /**
     * create instance and prepare shapes for visual effects
     * @param modifiers
     */
    public JLotosGraphMousePlugin(int modifiers, Factory<V> vertexFactory, Factory<E> edgeFactory) {
        super(modifiers);
        this.vertexFactory = vertexFactory;
        this.edgeFactory = edgeFactory;
        rawEdge.setCurve(0.0f, 0.0f, 0.33f, 100, .66f, -50,
                1.0f, 0.0f);
        rawArrowShape = ArrowFactory.getNotchedArrow(20, 16, 8);
        edgePaintable = new EdgePaintable();
        arrowPaintable = new ArrowPaintable();
		this.cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }
    
    /**
     * Overridden to be more flexible, and pass events with
     * key combinations. The default responds to both ButtonOne
     * and ButtonOne+Shift
     */
    @Override
    public boolean checkModifiers(MouseEvent e) {
        return (e.getModifiers() & modifiers) != 0;
    }

    /**
     * If the mouse is pressed in an empty area, create a new vertex there.
     * If the mouse is pressed on an existing vertex, prepare to create
     * an edge from that vertex to another
     */
    @SuppressWarnings("unchecked")
	public void mousePressed(MouseEvent e) {
        if(checkModifiers(e)) {
            final VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)e.getSource();
            final Point2D p = e.getPoint();
            GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
            	Graph<V,E> graph = vv.getModel().getGraphLayout().getGraph();
            	// set default edge type
            	if(graph instanceof DirectedGraph) {
            		edgeIsDirected = EdgeType.DIRECTED;
            	} else {
            		edgeIsDirected = EdgeType.UNDIRECTED;
            	}
            	
                final V vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
                if(vertex != null) { // get ready to make an edge
                    //startVertex = vertex;
                    //down = e.getPoint();
                    //transformEdgeShape(down, down);
                    //vv.addPostRenderPaintable(edgePaintable);
                    //if((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0
                    //		&& vv.getModel().getGraphLayout().getGraph() instanceof UndirectedGraph == false) {
                    //    edgeIsDirected = EdgeType.DIRECTED;
                    //}
                	if(!((EventNode)vertex).isAvailableEvent()){
                		JOptionPane.showMessageDialog(null,"Click on a available event.");
                	}else{
                		if(((EventNode)vertex).getOperation() != null){
                			((EventNode)vertex).getOperation().clean(((EventNode)vertex));
                		}
                		((EventNode)vertex).setAvailableEvent(false);
                		((JLotosFactory)vertexFactory).create(((EventNode)vertex), e.getX(), e.getY());
                		/*for (int i = 0; i < nextNodes.size(); i++) {
							V newVertex = (V)nextNodes.get(i);
							((EventNode)newVertex).setAvailableEvent(true);
                    		Layout<V,E> layout = vv.getModel().getGraphLayout();
                            graph.addVertex(newVertex);
                            layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(new Point(e.getX()+30, e.getY()+(40*(i+1)))));
                    	    E newEdge = edgeFactory.create();
                            graph.addEdge(newEdge, vertex, newVertex);	
						}*/
                	}
                    //if(edgeIsDirected == EdgeType.DIRECTED) {
                     //   transformArrowShape(down, e.getPoint());
                     //   vv.addPostRenderPaintable(arrowPaintable);
                    //}
                } else { // make a new vertex
                	JOptionPane.showMessageDialog(null,"Click on a available event.");
                    //V newVertex = vertexFactory.create();
                    //Layout<V,E> layout = vv.getModel().getGraphLayout();
                    //graph.addVertex(newVertex);
                    //layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));
                }
            }
            vv.repaint();
        }
    }
    
    /**
     * If startVertex is non-null, and the mouse is released over an
     * existing vertex, create an undirected edge from startVertex to
     * the vertex under the mouse pointer. If shift was also pressed,
     * create a directed edge instead.
     */
    @SuppressWarnings("unchecked")
	public void mouseReleased(MouseEvent e) {
        if(checkModifiers(e)) {
            final VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)e.getSource();
            final Point2D p = e.getPoint();
            Layout<V,E> layout = vv.getModel().getGraphLayout();
            GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
                final V vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
                if(vertex != null && startVertex != null) {
                    Graph<V,E> graph = 
                    	vv.getGraphLayout().getGraph();
                        graph.addEdge(edgeFactory.create(),
                        		startVertex, vertex, edgeIsDirected);
                    vv.repaint();
                }
            }
            startVertex = null;
            down = null;
            edgeIsDirected = EdgeType.UNDIRECTED;
            vv.removePostRenderPaintable(edgePaintable);
            vv.removePostRenderPaintable(arrowPaintable);
        }
    }

    /**
     * If startVertex is non-null, stretch an edge shape between
     * startVertex and the mouse pointer to simulate edge creation
     */
    @SuppressWarnings("unchecked")
    public void mouseDragged(MouseEvent e) {
        if(checkModifiers(e)) {
            if(startVertex != null) {
                transformEdgeShape(down, e.getPoint());
                if(edgeIsDirected == EdgeType.DIRECTED) {
                    transformArrowShape(down, e.getPoint());
                }
            }
            VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)e.getSource();
            vv.repaint();
        }
    }
    
    /**
     * code lifted from PluggableRenderer to move an edge shape into an
     * arbitrary position
     */
    private void transformEdgeShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);
        
        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        float dist = (float) Math.sqrt(dx*dx + dy*dy);
        xform.scale(dist / rawEdge.getBounds().getWidth(), 1.0);
        edgeShape = xform.createTransformedShape(rawEdge);
    }
    
    private void transformArrowShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);
        
        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        arrowShape = xform.createTransformedShape(rawArrowShape);
    }
    
    /**
     * Used for the edge creation visual effect during mouse drag
     */
    class EdgePaintable implements VisualizationServer.Paintable {
        
        public void paint(Graphics g) {
            if(edgeShape != null) {
                Color oldColor = g.getColor();
                g.setColor(Color.black);
                ((Graphics2D)g).draw(edgeShape);
                g.setColor(oldColor);
            }
        }
        
        public boolean useTransform() {
            return false;
        }
    }
    
    /**
     * Used for the directed edge creation visual effect during mouse drag
     */
    class ArrowPaintable implements VisualizationServer.Paintable {
        
        public void paint(Graphics g) {
            if(arrowShape != null) {
                Color oldColor = g.getColor();
                g.setColor(Color.black);
                ((Graphics2D)g).fill(arrowShape);
                g.setColor(oldColor);
            }
        }
        
        public boolean useTransform() {
            return false;
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
        JComponent c = (JComponent)e.getSource();
        c.setCursor(cursor);
    }
    public void mouseExited(MouseEvent e) {
        JComponent c = (JComponent)e.getSource();
        c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    public void mouseMoved(MouseEvent e) {}
}
