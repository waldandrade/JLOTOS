package br.com.jlotos.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.util.Map;

import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.FourPassImageShaper;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.LayeredIcon;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import br.com.jlotos.simulator.jung.DefaultVertexLabelRenderer;
import br.com.jlotos.simulator.jung.EditingModalGraphMouse;
import br.com.jlotos.simulator.jung.EventLink;
import br.com.jlotos.simulator.jung.EventNode;
import br.com.jlotos.simulator.jung.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicVertexRenderer;
import edu.uci.ics.jung.visualization.renderers.Checkmark;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import bus.compiling.semanticStructure.BehaviourExpression;
import bus.compiling.semanticStructure.Specification;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;


import java.awt.Container;

import javax.swing.ImageIcon;

import edu.uci.ics.jung.algorithms.layout.Layout;

import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.awt.event.ActionListener;

public class JLotosSimulator extends JApplet {
	

    Factory <EventNode> vertexFactory;
    Factory<EventLink> edgeFactory;
	
	private Specification specification;
	private BehaviourExpression actualBehaviour;

	private DirectedSparseMultigraph<EventNode,EventLink> graph;
	
	private ArrayList<EventNode> nodes = new ArrayList<EventNode>();
	
	private ArrayList<Integer> availableEvents = new ArrayList<Integer>();

    private Map<EventNode,Icon> iconMap = new HashMap<EventNode,Icon>();
    
 // a Map for the labels
    Map<EventNode,String> map = new HashMap<EventNode,String>();

    final GraphZoomScrollPane panel;
    
    /**
     * the visual component and renderer for the graph
     */
    private VisualizationViewer<EventNode,EventLink> vv;
    
    private final String PROCESS_ICON = "/br/com/jlotos/simulator/images/process.png";
	
	public JLotosSimulator(Specification specification){
		
		this.specification = specification;
			
		graph = new DirectedSparseMultigraph<EventNode, EventLink>();
				
		FRLayout2<EventNode, EventLink> layout = new FRLayout2<EventNode, EventLink>(graph);
        layout.setMaxIterations(100);
        layout.setInitializer(new RandomLocationTransformer<EventNode>(new Dimension(1000,400), 0));
        vv =  new VisualizationViewer<EventNode, EventLink>(layout, new Dimension(1000,450));
    	//Diz que o evento que terá alguma funcionalidade com o clique é o node 0.
		availableEvents.add(0);	
		EventNode starterNode = new EventNode(EventNode.START_NODE, "Start", graph);
		starterNode.setScope(specification);
		nodes.add(starterNode);
		
		showNodes();
        
        // This demo uses a special renderer to turn outlines on and off.
        // you do not need to do this in a real application.
        // Instead, just let vv use the Renderer it already has
        vv.getRenderer().setVertexRenderer(new DemoRenderer<EventNode,EventLink>());
        vv.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.E);
        
        Transformer<EventNode,Paint> vpf = 
            new PickableVertexPaintTransformer<EventNode>(vv.getPickedVertexState(), Color.white, Color.yellow);
        vv.getRenderContext().setVertexFillPaintTransformer(vpf);
        vv.getRenderContext().setEdgeDrawPaintTransformer(new PickableEdgePaintTransformer<EventLink>(vv.getPickedEdgeState(), Color.black, Color.cyan));

        vv.setBackground(Color.white);
        
        final Transformer<EventNode,String> vertexStringerImpl = 
            new VertexStringerImpl<EventNode,String>(map);
        vv.getRenderContext().setVertexLabelTransformer(vertexStringerImpl);
        vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.BLACK));
        vv.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.cyan));
//        vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Number,String>() {
//        	URL url = getClass().getResource("/images/lightning-s.gif");
//			public String transform(Number input) {
//				
//				return "<html><img src="+url+" height=10 width=21>"+input.toString();
//			}});
        
        // For this demo only, I use a special class that lets me turn various
        // features on and off. For a real application, use VertexIconShapeTransformer instead.
        final DemoVertexIconShapeTransformer<EventNode> vertexIconShapeTransformer =
            new DemoVertexIconShapeTransformer<EventNode>(new EllipseVertexShapeTransformer<EventNode>());
        
        final DemoVertexIconTransformer<EventNode> vertexIconTransformer =
        	new DemoVertexIconTransformer<EventNode>();
        
        vertexIconShapeTransformer.setIconMap(iconMap);
        vertexIconTransformer.setIconMap(iconMap);
        
        vv.getRenderContext().setVertexShapeTransformer(vertexIconShapeTransformer);
        vv.getRenderContext().setVertexIconTransformer(vertexIconTransformer);
        
        
     // Get the pickedState and add a listener that will decorate the
        // Vertex images with a checkmark icon when they are picked
        PickedState<EventNode> ps = vv.getPickedVertexState();
        ps.addItemListener(new PickWithIconListener<EventNode>(vertexIconTransformer));
        
        vv.addPostRenderPaintable(new VisualizationViewer.Paintable(){
            int x;
            int y;
            Font font;
            FontMetrics metrics;
            int swidth;
            int sheight;
            String str = getSpecification().getTitule().getConteudo();
            
            public void paint(Graphics g) {
                Dimension d = vv.getSize();
                if(font == null) {
                    font = new Font(g.getFont().getName(), Font.BOLD, 15);
                    metrics = g.getFontMetrics(font);
                    swidth = metrics.stringWidth(str);
                    sheight = metrics.getMaxAscent()+metrics.getMaxDescent();
                    x = (int)(100);
                    y = (int)(30);
                }
                g.setFont(font);
                Color oldColor = g.getColor();
                g.setColor(Color.BLACK);
                g.drawString(str, x, y);
                g.setColor(oldColor);
            }
            public boolean useTransform() {
                return false;
            }
        });

        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller<EventNode>());
        
        Container content = getContentPane();
        panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        
        vertexFactory = new JLotosFactory<EventNode>();
		
		edgeFactory = new Factory<EventLink>() {

			@Override
			public EventLink create() {
				// TODO Auto-generated method stub
				return new EventLink();
			}
		};
        
		EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(),this.vertexFactory, this.edgeFactory);
        
		vv.setGraphMouse(gm);
        vv.addKeyListener(gm.getModeKeyListener());

        vv.getRenderContext().setVertexShapeTransformer(vertexIconShapeTransformer);
        vv.getRenderContext().setVertexIconTransformer(vertexIconTransformer);
        
        
        final ScalingControl scaler = new CrossoverScalingControl();

        
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });

        JCheckBox shape = new JCheckBox("Shape");
        shape.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                vertexIconShapeTransformer.setShapeImages(e.getStateChange()==ItemEvent.SELECTED);
                vv.repaint();
            }
        });
        shape.setSelected(true);

        JCheckBox fill = new JCheckBox("Fill");
        fill.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                vertexIconTransformer.setFillImages(e.getStateChange()==ItemEvent.SELECTED);
                vv.repaint();
            }
        });
        fill.setSelected(true);
        
        JCheckBox drawOutlines = new JCheckBox("Outline");
        drawOutlines.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                vertexIconTransformer.setOutlineImages(e.getStateChange()==ItemEvent.SELECTED);
                vv.repaint();
            }
        });
        
        JComboBox modeBox = gm.getModeComboBox();
        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
        modePanel.add(modeBox);
        
        JPanel scaleGrid = new JPanel(new GridLayout(1,0));
        scaleGrid.setBorder(BorderFactory.createTitledBorder("Zoom"));
        JPanel labelFeatures = new JPanel(new GridLayout(1,0));
        labelFeatures.setBorder(BorderFactory.createTitledBorder("Image Effects"));
        JPanel controls = new JPanel();
        scaleGrid.add(plus);
        scaleGrid.add(minus);
        controls.add(scaleGrid);
        labelFeatures.add(shape);
        labelFeatures.add(fill);
        labelFeatures.add(drawOutlines);

        controls.add(labelFeatures);
        controls.add(modePanel);
        content.add(controls, BorderLayout.SOUTH);
        
	}

	public void showNodes() {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < availableEvents.size(); i++) {
			
			EventNode node = nodes.get(availableEvents.get(i));

			Layout<EventNode,EventLink> layout = vv.getModel().getGraphLayout();
			graph.addVertex(node);
            layout.setLocation(node, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(new Point(100, 100)));			
			
			try {
	            Icon icon = 
	               new LayeredIcon(new ImageIcon(JLotosSimulator.class.getResource(PROCESS_ICON)).getImage());
	           iconMap.put(nodes.get(availableEvents.get(i)), icon);
	        } catch(Exception ex) {
	             System.err.println("You need slashdoticons.jar in your classpath to see the image "+PROCESS_ICON);
	        }
	        map.put(nodes.get(availableEvents.get(i)), nodes.get(availableEvents.get(i)).getNodeDescription());
			
		}
		
	}
	
	public DirectedSparseMultigraph<EventNode, EventLink> getGraph() {
		return graph;
	}

	public void setGraph(DirectedSparseMultigraph<EventNode, EventLink> graph) {
		this.graph = graph;
	}

	public ArrayList<EventNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<EventNode> nodes) {
		this.nodes = nodes;
	}
	
	  /**
     * a special renderer that can turn outlines on and off
     * in this demo.
     * You won't need this for a real application.
     * Use BasicVertexRenderer instead
     * 
     * @author Tom Nelson
     *
     */
    class DemoRenderer<V,E> extends BasicVertexRenderer<V,E> {
        public void paintIconForVertex(RenderContext<V,E> rc, V v, Layout<V,E> layout) {
        	
            Point2D p = layout.transform(v);
            p = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p);
            float x = (float)p.getX();
            float y = (float)p.getY();

            GraphicsDecorator g = rc.getGraphicsContext();
            boolean outlineImages = false;
            Transformer<V,Icon> vertexIconFunction = rc.getVertexIconTransformer();
            
            if(vertexIconFunction instanceof DemoVertexIconTransformer) {
                outlineImages = ((DemoVertexIconTransformer<V>)vertexIconFunction).isOutlineImages();
            }
            Icon icon = vertexIconFunction.transform(v);
            if(icon == null || outlineImages) {
                
                Shape s = AffineTransform.getTranslateInstance(x,y).
                    createTransformedShape(rc.getVertexShapeTransformer().transform(v));
                paintShapeForVertex(rc, v, s);
            }
            if(icon != null) {
                int xLoc = (int) (x - icon.getIconWidth()/2);
                int yLoc = (int) (y - icon.getIconHeight()/2);
                icon.paintIcon(rc.getScreenDevice(), g.getDelegate(), xLoc, yLoc);
            }
        }
    }
    
    public static class DemoVertexIconTransformer<V> extends DefaultVertexIconTransformer<V>
		implements Transformer<V,Icon> {
	    
	    boolean fillImages = true;
	    boolean outlineImages = false;
	
	    /**
	     * @return Returns the fillImages.
	     */
	    public boolean isFillImages() {
	        return fillImages;
	    }
	    /**
	     * @param fillImages The fillImages to set.
	     */
	    public void setFillImages(boolean fillImages) {
	        this.fillImages = fillImages;
	    }
	
	    public boolean isOutlineImages() {
	        return outlineImages;
	    }
	    public void setOutlineImages(boolean outlineImages) {
	        this.outlineImages = outlineImages;
	    }
	    
	    public Icon transform(V v) {
	        if(fillImages) {
	            return (Icon)iconMap.get(v);
	        } else {
	            return null;
	        }
	    }
	}
    
    /**
     * When Vertices are picked, add a checkmark icon to the imager.
     * Remove the icon when a Vertex is unpicked
     * @author Tom Nelson
     *
     */
    public static class PickWithIconListener<V> implements ItemListener {
        DefaultVertexIconTransformer<V> imager;
        Icon checked;
        
        public PickWithIconListener(DefaultVertexIconTransformer<V> imager) {
            this.imager = imager;
            checked = new Checkmark();
        }

        public void itemStateChanged(ItemEvent e) {
            Icon icon = imager.transform((V)e.getItem());
            if(icon != null && icon instanceof LayeredIcon) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    ((LayeredIcon)icon).add(checked);
                } else {
                    ((LayeredIcon)icon).remove(checked);
                }
            }
        }
    }

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	
	/**
     * A simple implementation of VertexStringer that
     * gets Vertex labels from a Map  
     * 
     * @author Tom Nelson
     *
     *
     */
    public static class VertexStringerImpl<V,S> implements Transformer<V,String> {
        
        Map<V,String> map = new HashMap<V,String>();
        
        boolean enabled = true;
        
        public VertexStringerImpl(Map<V,String> map) {
            this.map = map;
        }
        
        /* (non-Javadoc)
         * @see edu.uci.ics.jung.graph.decorators.VertexStringer#getLabel(edu.uci.ics.jung.graph.Vertex)
         */
        public String transform(V v) {
            if(isEnabled()) {
                return map.get(v);
            } else {
                return "";
            }
        }
        
        /**
         * @return Returns the enabled.
         */
        public boolean isEnabled() {
            return enabled;
        }
        
        /**
         * @param enabled The enabled to set.
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
    
    /** 
     * this class exists only to provide settings to turn on/off shapes and image fill
     * in this demo.
     * In a real application, use VertexIconShapeTransformer instead.
     * 
     */
    public static class DemoVertexIconShapeTransformer<V> extends VertexIconShapeTransformer<V> {
        
        boolean shapeImages = true;

        public DemoVertexIconShapeTransformer(Transformer<V,Shape> delegate) {
            super(delegate);
        }

        /**
         * @return Returns the shapeImages.
         */
        public boolean isShapeImages() {
            return shapeImages;
        }
        /**
         * @param shapeImages The shapeImages to set.
         */
        public void setShapeImages(boolean shapeImages) {
            shapeMap.clear();
            this.shapeImages = shapeImages;
        }

        public Shape transform(V v) {
			Icon icon = (Icon) iconMap.get(v);

			if (icon != null && icon instanceof ImageIcon) {

				Image image = ((ImageIcon) icon).getImage();

				Shape shape = shapeMap.get(image);
				if (shape == null) {
					if (shapeImages) {
						shape = FourPassImageShaper.getShape(image, 30);
					} else {
						shape = new Rectangle2D.Float(0, 0, 
								image.getWidth(null), image.getHeight(null));
					}
                    if(shape.getBounds().getWidth() > 0 && 
                            shape.getBounds().getHeight() > 0) {
                        int width = image.getWidth(null);
                        int height = image.getHeight(null);
                        AffineTransform transform = 
                            AffineTransform.getTranslateInstance(-width / 2, -height / 2);
                        shape = transform.createTransformedShape(shape);
                        shapeMap.put(image, shape);
                    }
				}
				return shape;
			} else {
				return delegate.transform(v);
			}
		}
    }
    
    public class JLotosFactory<T> implements Factory<EventNode>{

 			@Override
 			public EventNode create() {
 				// TODO Auto-generated method stub
 				availableEvents.clear();
 				availableEvents.add(nodes.size());				
 				EventNode eventNode = new EventNode(nodes.size()+"", nodes.size()+"", graph);
 				nodes.add(eventNode);
 				
 				showNodes();
 				
 				return eventNode;
 			}
 			
 			public List<EventNode> create(EventNode dadNode, int x, int y) {
 				List<EventNode> nextNodes;
 				if(dadNode.getBehaviour() == null){
            		nextNodes = dadNode.getNextEventNodes(dadNode.getScope().getBehaviourExpression(), null);
        		}else{                    		
            		nextNodes = dadNode.getNextNodes(dadNode.getBehaviour(), null);
        		}
        		Layout<EventNode,EventLink> layout = vv.getModel().getGraphLayout();

        		//Teste para o choice
        		//Choice choice = new Choice(graph);
        		for (int i = 0; i < nextNodes.size(); i++) {
        			int newX = x+40;
        			int newY = y+(50*(i+1));
					EventNode newEventNode = nextNodes.get(i);
					newEventNode.setAvailableEvent(true);
                    graph.addVertex(newEventNode);
                   // newEventNode.setOperation(choice);
                   // choice.addEvent(newEventNode);
                    layout.setLocation(newEventNode, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(new Point(newX,newY)));
            	    try {
        	            Icon icon = 
        	               new LayeredIcon(new ImageIcon(JLotosSimulator.class.getResource(PROCESS_ICON)).getImage());
        	           iconMap.put(newEventNode, icon);
        	        } catch(Exception ex) {
        	             System.err.println("You need slashdoticons.jar in your classpath to see the image "+PROCESS_ICON);
        	        }
        	        map.put(newEventNode, newEventNode.getNodeDescription());
        	        EventLink newEdge = edgeFactory.create();
                    graph.addEdge(newEdge, dadNode, newEventNode);
                }
                return nextNodes;
 			}
    }
    /**
	 * a driver for this demo
	 *//*
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.add(new JLotosSimulator());
        frame.pack();
        frame.setVisible(true);
    }
	*/
}