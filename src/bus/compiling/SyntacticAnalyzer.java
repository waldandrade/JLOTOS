package bus.compiling;

import java.util.ArrayList;
import java.util.List;

import books.LotosOperation;
import bus.SystemHandler;
import bus.compiling.exceptions.LexicalErrorException;
import bus.compiling.exceptions.SyntaticErrorException;
import bus.compiling.semanticStructure.Action;
import bus.compiling.semanticStructure.BehaviourExpression;
import bus.compiling.semanticStructure.Functionality;
import bus.compiling.semanticStructure.Gate;
import bus.compiling.semanticStructure.ProcessDefinition;
import bus.compiling.semanticStructure.Scope;
import bus.compiling.semanticStructure.SelectPredicate;
import bus.compiling.semanticStructure.Specification;
import bus.compiling.semanticStructure.TypeDefinition;
import bus.compiling.semanticStructure.behaviourExpression.Exit;
import bus.compiling.semanticStructure.syncronize.EventGate;
import bus.compiling.semanticStructure.syncronize.ValueDeclaration;
import bus.compiling.semanticStructure.syncronize.VariableDeclaration;
import bus.compiling.semanticStructure.typeDefinition.Equation;
import bus.compiling.semanticStructure.typeDefinition.EquationCollection;
import bus.compiling.semanticStructure.typeDefinition.OperatorCollection;
import bus.compiling.semanticStructure.typeDefinition.Sort;

public class SyntacticAnalyzer {
	
	public Token actualToken = null;
	
	ArrayList<Token> codTokens = null;
	ArrayList<Token> commentTokens = null;
	private OperatorCollection actualOperatorCollection = null;
		
	public int indexToken = 0;
	
	public final String FIRST_OP = "FIRST_OP";
	public final String MIDDLE_OP = "MIDDLE_OP";
	public final String LAST_OP = "LAST_OP";
	
	public final String NOT_FOUND_OP = "NOT_FOUND_OP";
	
	public final String WRONG = "#WRONG#";
	
	public SemanticTable semanticTable;
	
	public Specification specification = null;
	    
	public Scope actualScope = null;
	
    public Token getToken(){
    	Token actualToken = null;
    	if(indexToken<codTokens.size()) actualToken = codTokens.get(indexToken);
    	return actualToken;
    }
	
	public Token currentToken() {
			actualToken = getToken();
			
			return actualToken;
	}
	
    public void tokenOK(){
    	indexToken++;
    }

	public Specification analyse(ArrayList<ArrayList<Token>> tokensGroups) throws SyntaticErrorException{
				
		this.codTokens = tokensGroups.get(0);
		this.commentTokens = tokensGroups.get(1);
				
		initDown();
		
		return specification;
		
	}
	
	public boolean initDown() throws SyntaticErrorException{
		//System.out.println(programa());
		currentToken();
		if(actualToken!=null){
			if(actualToken.getConteudo().equals("process")){
				if(!P()){
					return false;
				}
			}else if(actualToken.getConteudo().equals("specification")){
				if(!spec()){
					return false;
				}
			}else throw new SyntaticErrorException("Specifications need start with \"process\" or \"specification\" tokens+" +
					"", indexToken);
		}else{
			throw new SyntaticErrorException("Specification is empty!!", indexToken);
		}
		return true;
	}
	
	
	public boolean spec() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals("specification")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"specification\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"specification\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
				
		
		//Difine the specification
		specification = new Specification();
		specification.setTokenId(indexToken);
		actualScope = specification;
						
		//getActualScope().setGlobalOperationCollection(actualOperatorCollection);
			
		tokenOK();
		if(!decl())return false;
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals(":")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \":\"!!", indexToken);
			throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		if(!func())return false;
				
		typeD();//deve ser tratado interno por ser opcional
				
		specification.globalToInnerTD();
		
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals("behaviour")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"behaviour\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"behaviour\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		
		//trecho modificado
		BehaviourExpression behaviourExpression = B();
		if(behaviourExpression == null)
			throw new SyntaticErrorException("There are no correct behaviours", indexToken);
		//é tratado interno isso deve ser respeitado
		
		
		specification.setBehaviourExpression(behaviourExpression);
		
		hideGatesIn(specification.getBehaviourExpression(), null);
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a \"where\", " +
						"or a \"endspec\"!!", indexToken);
		else if(actualToken.getConteudo().equals("where")){
			
			tokenOK();
			
			
			typeD();//deve ser tratado interno por ser opcional
			
			
			
			currentToken();
			if(actualToken==null){
				throw new SyntaticErrorException("Interrupted specification, specting a " +
						"process definition!!", indexToken);
			}else if(actualToken.getConteudo().equals("process"))
				if(!P())return false;
		}
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals("endspec")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"endspec\", " +
						"or another process definition, and so a \"endspec\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"endspec\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
		
		return true;
	}
	
	public boolean func() throws SyntaticErrorException{
		currentToken();
		
		Functionality functionality = null;
		
		if(actualToken==null || (!actualToken.getConteudo().equals("exit")&&!actualToken.getConteudo().equals("noexit"))){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"exit\" or a \"noexit\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"exit\" or a , and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
		tokenOK();
		//Define the specification functionality
		if(actualToken.getConteudo().equals("exit")){
			currentToken();
			if(actualToken == null){
				throw new SyntaticErrorException("Interrupted specification", indexToken);
			}
			if(actualToken.getConteudo().equals("(")){
				functionality = new Functionality(Functionality.DATA);
				tokenOK();
				List<Sort> funcSorts = funcSortList();
				functionality.setFuncSorts(funcSorts);
				currentToken();
				if(actualToken == null){
					throw new SyntaticErrorException("Interrupted specification", indexToken);
				}
				if(!actualToken.getConteudo().equals(")")){
					throw new SyntaticErrorException("You need to close the the parenthesis with ')'!", indexToken);
				}
				tokenOK();
			}else{
				functionality = new Functionality(Functionality.EXIT);
			}
		}else{
			functionality = new Functionality(Functionality.NO_EXIT);
		}
		
		actualScope.setFunctionality(functionality);
		
		return true;
	}
	
	public List<Sort> funcSortList() throws SyntaticErrorException{
		
		currentToken();
		if(actualToken == null){
			throw new SyntaticErrorException("Interrupted specification", indexToken);
		}
		

		List<Sort> sortList = new ArrayList<Sort>();
		
		sortList.add(seachForSort(actualToken, specification));
		 
		currentToken();
		
		if(actualToken == null){
			throw new SyntaticErrorException("Interrupted specification", indexToken);
		}
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			sortList.addAll(funcSortList());
			
		}
		
		return sortList;
	}
	
	private Sort seachForSort(Token actualToken2, Scope scope) {
		
		if(scope == null){
			return null;
		}
		
		if(scope.getScopeSorts() != null){
						
			for (int i = 0; i < scope.getScopeSorts().size(); i++) {
				
				if(scope.getScopeSorts().get(i).getSortName().equals(actualToken2.getConteudo())){
					return scope.getScopeSorts().get(i);
				}
				
			}
		}
		
		if((scope instanceof Specification) && ((Specification)scope).getFlagInnerOrGlobalTypeDefinition()){
			((Specification)scope).setFlagInnerOrGlobalTypeDefinition(false);
			return seachForSort(actualToken2, scope);
		}
		
		return seachForSort(actualToken2, scope.getDadScope());
	}
	
	private TypeDefinition actualTypeDefinition = null;

	public void typeD() throws SyntaticErrorException{
		currentToken();
		
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a \"type\" or a \"lybrary\", or " +
				"a process definition!!", indexToken);
		else if(actualToken.getConteudo().equals("library")){
			tokenOK();
			currentToken();
			List<Token> libraryTokens = libraryList();
			currentToken();
			if(actualToken==null || !actualToken.getConteudo().equals("endlib"))throw new SyntaticErrorException("Interrupted specification, specting a \"endlib\"!!", indexToken);
			tokenOK();
			
			
			if(libraryTokens != null && libraryTokens.size() > 0){
			codTokens.addAll(indexToken, libraryTokens);
	
			}
			/*	
			if(!typeD()){
				return false;
			}
			if(libraryTokens != null && libraryTokens.size() > 0){
				for (Token token : libraryTokens) {

					try {
						codTokens.addAll(indexToken, SystemHandler.getLibraryTokens(token.getConteudo()) );
					} catch (LexicalErrorException e) {
						e.printStackTrace();
					}
				}
			
			}
			*/
			
			
			typeD();//deve ser tratado interno por ser opcional
						
			
		}else if(actualToken.getConteudo().equals("type")){
			tokenOK();
			currentToken();
			TypeDefinition typeDefinition = new TypeDefinition();			
			actualTypeDefinition = typeDefinition;
			actualScope.addTypeDefinition(0,typeDefinition);
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
							"\n\t", indexToken);
			}
			
			typeDefinition.setTypeTitule(actualToken);
			
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getConteudo().equals("is")){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \"is\"!!", indexToken);
				throw new SyntaticErrorException("Need a \"is\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			
			tokenOK(); 
			currentToken();
			
			if(actualToken == null)throw new SyntaticErrorException("Interrupted specification, specting <IDENTIFIER>!!", indexToken);
			else if(actualToken.getTipo().equals(SystemHandler.lotosBook.ID)&&!actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){	
				List<TypeDefinition> extendingTypeDefinitions  = typeDefinitionExtendingList();
				if(extendingTypeDefinitions == null){
					throw new SyntaticErrorException("Not found type definitions to extend.", indexToken);
				}
				
				for (TypeDefinition typeDefinition2 : extendingTypeDefinitions) {
					
					if(typeDefinition2.getOperatorCollection() != null && typeDefinition2.getOperatorCollection().getPossibleOperations() != null){				
						typeDefinition.setOperatorCollection(new OperatorCollection());
						typeDefinition.getOperatorCollection().addAllOperation(typeDefinition2.getOperatorCollection().getPossibleOperations());
					}
					
					if(typeDefinition2.getSorts() != null){
						typeDefinition.addAllSorts(typeDefinition2.getSorts());
					}
					
				}
				
				//typeDefinition.setExtendingTypeDefinition(extendingTypeDefinitions);
			}
			
			currentToken();
			if(actualToken == null)throw new SyntaticErrorException("Interrupted specification, specting <IDENTIFIER>!!", indexToken);
			else if(actualToken.getConteudo().equals("renamedby")){
				tokenOK();
				if(!renamedTypeDFunc()){
					throw new SyntaticErrorException("There was a error in type renaming declaration.", indexToken);
				}
			}else{
			
			actualOperatorCollection = new OperatorCollection();
				
			if(!typeDFunc()){
				throw new SyntaticErrorException("There was a error in type declaration.", indexToken);
			}
			}
			currentToken();
			if(actualToken==null||!actualToken.getConteudo().equals("endtype")){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \"endtype\"!!", indexToken);
				throw new SyntaticErrorException("Need a \"endtype\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			
						
			typeD();//deve ser tratado interno por ser opcional
			
		}
		
	}
	
	private boolean renamedTypeDFunc() throws SyntaticErrorException{
		
		currentToken();
		
		sortNames();
			
		currentToken();
			
		if(actualToken == null)throw new SyntaticErrorException("Interrupted specification, specting <IDENTIFIER>!!", indexToken);
		else if(actualToken.getConteudo().equals("opnnames")){
		
			
		}
		
		return true;
	}
	
	private boolean sortNames() throws SyntaticErrorException{
		
		currentToken();
		if(actualToken == null)throw new SyntaticErrorException("Interrupted specification, specting <IDENTIFIER>!!", indexToken);
		else if(actualToken.getConteudo().equals("sortnames")){
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
			}
			String sortName = actualToken.getConteudo();
			tokenOK();
			forstrut(sortName);
			sortNames();
		}
		
		return true;
	}

	private void forstrut(String sortName) throws SyntaticErrorException{
		
		currentToken();
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		if(!actualToken.getConteudo().equals("for"))throw new SyntaticErrorException("Need a \"for\"", indexToken);
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		Sort sort = searchSort(actualTypeDefinition, actualToken.getConteudo());
		if(sort == null){
			throw new SyntaticErrorException("There are no sorts with given name.", indexToken);
		}
		Sort renamedSort = sort.getSortClone();
		renamedSort.setSortName(sortName);
		actualTypeDefinition.addSort(renamedSort);
		renamedSort.setTypeDefinitionReference(actualTypeDefinition);
		actualScope.addSortToScope(renamedSort);
		
		tokenOK();		
		
		
	}

	public boolean typeDFunc() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else{
			if(actualToken.getConteudo().equals("formalsorts")){
				tokenOK();
				currentToken();
				if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
					throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
							"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
							"\n\t", indexToken);
				}
				tokenOK();
				currentToken();
				if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
				else if (actualToken.getConteudo().equals("formalopns")){
					lotosOperations = new ArrayList<LotosOperation>();
					tokenOK();
					
					List<LotosOperation> aux = operList();
					if(aux != null)
					lotosOperations.addAll(aux);
					else
						throw new SyntaticErrorException("You most to declare operations, 'opns'", indexToken);
					currentToken();
					if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
					else if(!actualToken.getConteudo().equals(":")){
						throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
					}
					tokenOK();
					
					lotosOperations = domainSorts(lotosOperations);
					currentToken();
					if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
					else if(!actualToken.getConteudo().equals("->")){
						throw new SyntaticErrorException("Need a \"->\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
					}
					tokenOK();
					currentToken();
					if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
						if(actualToken==null)
							throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
						throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
								"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
								"\n\t", indexToken);
					}
					for (int i = 0; i < lotosOperations.size(); i++) {
						lotosOperations.get(i).setReturnSort(actualToken.getConteudo());
						actualOperatorCollection.addOperation(lotosOperations.get(i));
					}
					tokenOK();
					if(!opnsStrut())return false;
					currentToken();
					if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
					else if (actualToken.getConteudo().equals("formaleqns")){
					tokenOK();

					actualTypeDefinition.setOperatorCollection(actualOperatorCollection);
					
					//OperatorCollection opc2 = new OperatorCollection();
					//opc2.operatorCollectionDad = actualOperatorCollection;
					//actualOperatorCollection = opc2;
					
					//getActualScope().setScopeOperationCollection(actualOperatorCollection);
					
					if(!eqnsStrut())return false;
										
					currentToken();
					}
				}
			}
		}
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if (actualToken.getConteudo().equals("sorts")){
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
			}
			
			Sort sort = new Sort();
			sort.setSortName(actualToken.getConteudo());
			actualTypeDefinition.addSort(sort);
			sort.setTypeDefinitionReference(actualTypeDefinition);
			actualScope.addSortToScope(sort);
			
			tokenOK();
			currentToken();
		}
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if (actualToken.getConteudo().equals("opns")){
			tokenOK();
			lotosOperations = new ArrayList<LotosOperation>();
			lotosOperations.addAll(operList());
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			else if(!actualToken.getConteudo().equals(":")){
				throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			lotosOperations = domainSorts(lotosOperations);
			
			
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			else if(!actualToken.getConteudo().equals("->")){
				throw new SyntaticErrorException("Need a \"->\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
			}
			searchSort(actualTypeDefinition, actualToken.getConteudo());
			for (int i = 0; i < lotosOperations.size(); i++) {
				lotosOperations.get(i).setReturnSort(actualToken.getConteudo());
				actualOperatorCollection.addOperation(lotosOperations.get(i));
			}
			tokenOK();
			if(!opnsStrut())return false;
			currentToken();
			actualTypeDefinition.setOperatorCollection(actualOperatorCollection);
		}
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if (actualToken.getConteudo().equals("eqns")){
			tokenOK();
			
			//getActualScope().setScopeOperationCollection(actualOperatorCollection);
			
			if(!eqnsStrut())return false;
			
			
			
			currentToken();
		}
		
		return true;
	}
	
	
	//Restrição criar operações iguais a caracteres reservados do LOTOS... como _=_ ou _,_ ..... ele pode identificar 
	public List<LotosOperation> operList() throws SyntaticErrorException{
		
		List<LotosOperation> actualLotosOperations = new ArrayList<LotosOperation>();
		LotosOperation newLotosOperation = new LotosOperation();		
		
		currentToken();
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		if(actualToken.getTipo().equals(SystemHandler.lotosBook.LR_OPERAND)){
			newLotosOperation = extractTokenLR(newLotosOperation);
			tokenOK();
		}else if(actualToken.getTipo().equals(SystemHandler.lotosBook.R_OPERAND)){
			newLotosOperation = extractTokenR(newLotosOperation);
			tokenOK();
		}else if(actualToken.getTipo().equals(SystemHandler.lotosBook.L_OPERAND)){
			newLotosOperation  = extractTokenL(newLotosOperation);
			tokenOK();
			newLotosOperation = appendOperator(newLotosOperation);
			currentToken();
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \":\"!!", indexToken);
			if(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND_UNDERLINE)){
				actualToken.setTipo(SystemHandler.lotosBook.R_UNDERLINE);
				newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
				tokenOK();
			}
		}else if(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND_UNDERLINE)){
			actualToken.setTipo(SystemHandler.lotosBook.L_UNDERLINE);
			newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
			tokenOK();
			currentToken();
			if(actualToken.getTipo().equals(SystemHandler.lotosBook.R_OPERAND)){
				newLotosOperation = extractTokenR(newLotosOperation);
				actualOperatorCollection.addOperation(newLotosOperation);
				tokenOK();
			}else
			if((actualToken.getTipo().equals(SystemHandler.lotosBook.ID)|| (actualToken.getTipo().equals(SystemHandler.lotosBook.OPERATOR))||(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)))&& !actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				actualToken.setTipo(SystemHandler.lotosBook.OPERATOR_KEY);
				newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
				tokenOK();
				newLotosOperation = appendOperator(newLotosOperation);
				currentToken();
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \":\"!!", indexToken);
				if(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND_UNDERLINE)){
					actualToken.setTipo(SystemHandler.lotosBook.R_UNDERLINE);
					newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
					tokenOK();
				}				
			}else{
				throw new SyntaticErrorException("Interrupted specification, specting a \"OPERATION\"!!", indexToken);
			}
			
		}else if((actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)))&&!actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			actualToken.setTipo(SystemHandler.lotosBook.OPERATOR_KEY);
			newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
			tokenOK();
		}else{
			
			
			return null;
		}
		

		actualLotosOperations.add(newLotosOperation);
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			List<LotosOperation> aux = operList();
			if(aux!=null){
				actualLotosOperations.addAll(aux);
			}else{
				throw new SyntaticErrorException("Require a new operator declaration", indexToken);
			}
		}
		
		
		return actualLotosOperations;
		
	}
	
	private LotosOperation appendOperator(LotosOperation newLotosOperation) throws SyntaticErrorException{
		
		int endPastToken = actualToken.getFim();
		
		currentToken();
		
		if((actualToken.getTipo().equals(SystemHandler.lotosBook.ID)|| (actualToken.getTipo().equals(SystemHandler.lotosBook.OPERATOR))||(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)))&& !actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK) && endPastToken == actualToken.getInicio()){
			
			newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
			codTokens.get(indexToken-1).setFim(actualToken.getFim());
			codTokens.get(indexToken-1).setConteudo(codTokens.get(indexToken-1).getConteudo()+actualToken.getConteudo());
			codTokens.remove(indexToken);
			
			newLotosOperation = appendOperator(newLotosOperation);
		}
			
		return newLotosOperation;
	}

		
	private EquationCollection actualEquationCollection = null;
	//O erro está aqui.
	public boolean eqnsStrut() throws SyntaticErrorException{
		String equationOfSort = "UNDEFINED";
		currentToken();
		Sort ofSort = null;
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		else if(actualToken.getConteudo().equals("forall")){

			actualEquationCollection = new EquationCollection();
			tokenOK();
			if(!forallStrut())return false;
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			else if(!actualToken.getConteudo().equals("ofsort")){
				throw new SyntaticErrorException("Need a \"ofsort\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
			}
			equationOfSort = actualToken.getConteudo();
			
			ofSort = searchSort(actualTypeDefinition,equationOfSort);
			
			if(ofSort == null){
				throw new SyntaticErrorException("This sort not exists on the acessible scope of this process.", indexToken);
			}
			
			actualEquationCollection.setEquationOfSort(ofSort);
			
			tokenOK();
		}else if(actualToken.getConteudo().equals("ofsort")){

			actualEquationCollection = new EquationCollection();
			tokenOK();
			currentToken();
			if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
						"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
			}
			
			
			equationOfSort = actualToken.getConteudo();
			
			ofSort = searchSort(actualTypeDefinition,equationOfSort);
			
			if(ofSort == null){
				throw new SyntaticErrorException("This sort not exists on the acessible scope of this process.", indexToken);
			}
			
			actualEquationCollection.setEquationOfSort(ofSort);
			
			
			tokenOK();
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			if(actualToken.getConteudo().equals("forall")){
				tokenOK();
				if(!forallStrut())return false;
			}
		}
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else{
			if(!eqns(ofSort))return false;
			
			actualTypeDefinition.addEquantionCollection(actualEquationCollection);
			
			ofSort.addEquationsOfSort(actualEquationCollection);
			
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			else if(actualToken.getConteudo().equals("forall")||actualToken.getConteudo().equals("ofsort")){
				
				if(!eqnsStrut())return false;
								
				currentToken();
			}
		}
		return true;
	}
	
	private Sort searchSort(TypeDefinition actualTypeDefinition2,
			String sort) throws SyntaticErrorException{
		// TODO Auto-generated method stub
		
		if(actualTypeDefinition2.getSorts() == null || actualTypeDefinition2.getSorts().size() == 0){
			throw new SyntaticErrorException("There are no sorts with the given name.", indexToken);
		}
		
		for (int i = 0; i < actualTypeDefinition2.getSorts().size(); i++) {
			
			if(actualTypeDefinition2.getSorts().get(i).getSortName().equalsIgnoreCase(sort)){
				return actualTypeDefinition2.getSorts().get(i);
			}
			
		}
		
		throw new SyntaticErrorException("There are no sorts with the given name.", indexToken);

	}

	private Sort publishSort(Scope actualScope2, String sort) {
		
		// TODO Auto-generated method stub
		
		
		if(actualScope2 != null){
			
			if(actualScope2.getScopeSorts() == null){
				
				if((actualScope2 instanceof Specification) && ((Specification)actualScope2).getFlagInnerOrGlobalTypeDefinition()){
					((Specification)actualScope2).setFlagInnerOrGlobalTypeDefinition(false);
					Sort returnSort = publishSort(actualScope2, sort);
					if(returnSort == null)return null;
					else return returnSort.getSortClone();
				}else{
					
					Sort returnSort = publishSort(actualScope2.getDadScope(), sort);
					if(returnSort == null)return null;
					else return returnSort.getSortClone();
				}
			}else{
				
				for (int i = 0; i < actualScope2.getScopeSorts().size(); i++) {
					
					if(actualScope2.getScopeSorts().get(i).getSortName().equals(sort)){
						return actualScope2.getScopeSorts().get(i);
					}
					
				}
			
				if((actualScope2 instanceof Specification) && ((Specification)actualScope2).getFlagInnerOrGlobalTypeDefinition()){
					((Specification)actualScope2).setFlagInnerOrGlobalTypeDefinition(false);
					
					Sort returnSort = publishSort(actualScope2, sort);
					if(returnSort == null) return null;
					else return returnSort.getSortClone();
				}else{
					Sort returnSort = publishSort(actualScope2.getDadScope(), sort);
					if(returnSort == null) return null;
					else return returnSort.getSortClone();
				}
			}
					
		}
		
		return null;
	}

	public boolean forallStrut() throws SyntaticErrorException{
		currentToken();
		
		//alterar para outro método que vai adicionar semântica ao forall
		
		List<LotosOperation> foralLotosOperations =  getForallOperationList();
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if(!actualToken.getConteudo().equals(":")){
			throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		searchSort(actualTypeDefinition, actualToken.getConteudo());
		for (int i = 0; i < foralLotosOperations.size(); i++) {
			foralLotosOperations.get(i).setReturnSort(actualToken.getConteudo());
			actualOperatorCollection.addOperation(foralLotosOperations.get(i));
		}
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			forallStrut();
		}
		return true;
	}
	
	private List<LotosOperation> getForallOperationList()  throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		
		LotosOperation newLotosOperation = new LotosOperation();
		newLotosOperation.addToken(actualToken.getConteudo(), indexToken);
		
		List<LotosOperation> newLotosOperationList = new ArrayList<LotosOperation>();
		
		newLotosOperationList.add(newLotosOperation);
		
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\", or a \"->\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			newLotosOperationList.addAll(getForallOperationList());
		}	

		return newLotosOperationList;
	}

	Equation equation = null;
	
	public boolean eqns(Sort ofSort) throws SyntaticErrorException{
		currentToken();
		
		equation = new Equation();
		
		actualEquationCollection.addEquation(equation);
		
		equation.setOfSort(ofSort);
				
		List <OpTable> eqnsDataExpList = dataExpList(null, ofSort.getSortName(), 0);
		
		if(eqnsDataExpList == null || eqnsDataExpList.size() == 0){
			throw new SyntaticErrorException("There isn't arguments in equation.", indexToken);
		}
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if (!actualToken.getConteudo().equals("=")){
			if(actualToken.getConteudo().equals("=>")){
				
				for (int i = 0; i < eqnsDataExpList.size(); i++) {
					if(!eqnsDataExpList.get(i).getOpReturnSort().equalsIgnoreCase("bool")){
						throw new SyntaticErrorException("A implication must have only Bool data as arguments.", indexToken);
					}			
				}
				
				equation.addAllImplicationBooleansEqnsOpTables(eqnsDataExpList);
				
				tokenOK();
				
				actualEquation = equation;
				
				implication(ofSort.getSortName());
			}else{
				throw new SyntaticErrorException("Need a \"=\" 1, and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
		}else{
		
		if(eqnsDataExpList.size() > 1){
			throw new SyntaticErrorException("There are more arguments then is necessary.", indexToken);
		}
			
		equation.setLeftEqnsOpTable(eqnsDataExpList.get(0));
		
		tokenOK();
		//if(!dataExp())throw new SyntaticErrorException("There are no data expression to exchange.", indexToken);
		OpTable opTable = dataExp(ofSort.getSortName(), false);
		if(opTable == null){
			throw new SyntaticErrorException("Need a data expression 1!",indexToken);
		}
		
		equation.setRightEqnsOpTable(opTable);
		
		}
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if (!actualToken.getConteudo().equals(";")){
			throw new SyntaticErrorException("Need a \";\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if(actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND) && (!actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK))){
			if(!eqns(ofSort))return false;
		}
		return true;
	}
	
	private Equation actualEquation = null;
	
	private void implication(String implicationSort) throws SyntaticErrorException{
		currentToken();
		//if(!dataExp())throw new SyntaticErrorException("Need a data expression!",indexToken);
		OpTable opTable = dataExp(implicationSort, false);
		if(opTable == null){
			throw new SyntaticErrorException("Need a data expression 2!",indexToken);
		}
	
		actualEquation.setLeftEqnsOpTable(opTable);
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if (!actualToken.getConteudo().equals("=")){
				throw new SyntaticErrorException("Need a \"=\" 2, and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		//if(!dataExp())throw new SyntaticErrorException("There are no data expression to exchange.", indexToken);
		OpTable opTable2 = dataExp(implicationSort, false);
		if(opTable2 == null){
			throw new SyntaticErrorException("Need a data expression 3!",indexToken);
		}
		
		actualEquation.setRightEqnsOpTable(opTable2);
	}

	public boolean valueExpression() throws SyntaticErrorException{
		currentToken();
		if(!value())return false;
		currentToken();
		if(!opValue())return false;
		currentToken();
		return true;
	}
	
	public boolean opValue() throws SyntaticErrorException{
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)){
			tokenOK();
			currentToken();
			if(!value())return false;
			currentToken();
			if(!opValue())return false;
			currentToken();
		}
		return true;
	}
	
	public boolean value() throws SyntaticErrorException{
		
		currentToken();
		if(actualToken==null || (!actualToken.getTipo().equals(SystemHandler.lotosBook.ID)&&!actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)) ||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		tokenOK();
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		else if (actualToken.getConteudo().equals("(")){
			tokenOK();
			if(!valueExpression())return false;
			if(!valueList())return false;
			currentToken();
			if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
			else if(!actualToken.getConteudo().equals(")")){
				throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
		}
		return true;
	}
	
	public boolean valueList() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			currentToken();
			if(!valueExpression())return false;
			currentToken();
			valueList();
		}
		
		return true;
	}
	
	public LotosOperation extractTokenLR(LotosOperation lotosOperation) throws SyntaticErrorException{
		currentToken();
		
		
		Token tk1 = new Token(actualToken.getConteudo().substring(0, 1), SystemHandler.lotosBook.L_UNDERLINE, actualToken.getInicio(), actualToken.getInicio()+1, true);
		lotosOperation.addToken(tk1.getConteudo(), indexToken);
				
		codTokens.set(indexToken, tk1);
		
		Token tk2 = new Token(actualToken.getConteudo().substring(1, actualToken.getConteudo().length()-1), SystemHandler.lotosBook.OPERATOR_KEY, actualToken.getInicio()+1, actualToken.getFim()-1, true);
		lotosOperation.addToken(tk2.getConteudo(), ++indexToken);
		
		codTokens.add(indexToken, tk2);
		
		Token tk3 = new Token(actualToken.getConteudo().substring(actualToken.getConteudo().length()-1), SystemHandler.lotosBook.R_UNDERLINE, actualToken.getFim()-1, actualToken.getFim(), true);
		lotosOperation.addToken(tk3.getConteudo(), ++indexToken);
		
		codTokens.add(indexToken, tk3);
				
		return lotosOperation;
	}
	
	public LotosOperation extractTokenL(LotosOperation lotosOperation) throws SyntaticErrorException{
		currentToken();
		
		
		Token tk1 = new Token(actualToken.getConteudo().substring(0, 1), SystemHandler.lotosBook.L_UNDERLINE, actualToken.getInicio(), actualToken.getInicio()+1, true);
		lotosOperation.addToken(tk1.getConteudo(), indexToken);
		
		codTokens.set(indexToken, tk1);
		
		Token tk2 = new Token(actualToken.getConteudo().substring(1, actualToken.getConteudo().length()), SystemHandler.lotosBook.OPERATOR_KEY, actualToken.getInicio()+1, actualToken.getFim(), true);
		lotosOperation.addToken(tk2.getConteudo(), ++indexToken);
		
		codTokens.add(indexToken, tk2);
		
		return lotosOperation;
		
	}
	
	public LotosOperation extractTokenR(LotosOperation lotosOperation) throws SyntaticErrorException{
		currentToken();
		
		
		Token tk2 = new Token(actualToken.getConteudo().substring(0, actualToken.getConteudo().length()-1), SystemHandler.lotosBook.OPERATOR_KEY, actualToken.getInicio(), actualToken.getFim()-1, true);
		lotosOperation.addToken(tk2.getConteudo(), indexToken);

		codTokens.set(indexToken, tk2);
		
		Token tk3 = new Token(actualToken.getConteudo().substring(actualToken.getConteudo().length()-1), SystemHandler.lotosBook.R_UNDERLINE, actualToken.getFim()-1, actualToken.getFim(), true);
		lotosOperation.addToken(tk3.getConteudo(), ++indexToken);
		
		codTokens.add(indexToken, tk3);
		
		return lotosOperation;
	}
	
	private List<LotosOperation> lotosOperations = null;
	
	public boolean opnsStrut() throws SyntaticErrorException{
		lotosOperations = new ArrayList<LotosOperation>();
		
		List<LotosOperation> aux = operList();
		
		if(aux!=null){
		lotosOperations.addAll(aux);
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if(!actualToken.getConteudo().equals(":")){
			throw new SyntaticErrorException("Need a 1 \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		
		lotosOperations = domainSorts(lotosOperations);
		
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		else if(!actualToken.getConteudo().equals("->")){
			throw new SyntaticErrorException("Need a \"->\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		searchSort(actualTypeDefinition, actualToken.getConteudo());
		for (int i = 0; i < lotosOperations.size(); i++) {
			lotosOperations.get(i).setReturnSort(actualToken.getConteudo());
			actualOperatorCollection.addOperation(lotosOperations.get(i));
		}
		tokenOK();
		if(!opnsStrut())return false;
		
		}
		return true;
	}
	
	public List<LotosOperation> domainSorts(List<LotosOperation> lotosOperations) throws SyntaticErrorException{
		
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		}else
		if(actualToken.getTipo().equals(SystemHandler.lotosBook.ID)&&!actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			
			searchSort(actualTypeDefinition, actualToken.getConteudo());
			for (int i = 0; i < lotosOperations.size(); i++) {
				lotosOperations.get(i).addSort(actualToken.getConteudo());
			}
			tokenOK();
			currentToken();
			if(actualToken==null){
				throw new SyntaticErrorException("Interrupted specification, specting a \",\", or a \"]\"!!", indexToken);
			}else
			if(actualToken.getConteudo().equals(",")){
				tokenOK();
				lotosOperations = sortList(lotosOperations);
			}		
		}
		
		return lotosOperations;
		
	}

	public List<LotosOperation> sortList(List<LotosOperation> lotosOperations) throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		for (int i = 0; i < lotosOperations.size(); i++) {
			lotosOperations.get(i).addSort(actualToken.getConteudo());
		}
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\", or a \"->\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			lotosOperations = sortList(lotosOperations);
		}	

		return lotosOperations;
		
	}
	
		
	//process-definitions que devem ser tratados no escopo externo
	public boolean P()throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals("process")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"process\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"process\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
		//ProcessDefinition process = new ProcessDefinition(actualToken.getConteudo());
		//process.setDadScope(getActualScope());
		//process.setDefTokenId(indexToken);
		
		//semanticTable.setSeenScope(process);
		
		ProcessDefinition processDefinition = new ProcessDefinition();
		
		processDefinition.setDadScope(actualScope);
		
		processDefinition.setTokenId(indexToken);
		actualScope = processDefinition;
		
		

		//if(getActualScope().getDadScope()!=null)
		//getActualScope().getDadScope().addProcess(process);
		//else
		//semanticTable.setGlobalScope(process);
		
		//Definir um novo operatorCollection para esse processo que vai visualizar o sua coleção de operadores e 
		//a coleção de operadores do seu pai, e do pai de seu pai
		tokenOK();
		if(!decl())return false;
		tokenOK();
		currentToken();
		//adicionar um lotosOperrationCollection
			
		//getActualScope().setParametrizedOperationCollection(actualOperatorCollection);
		
		if(actualToken.getConteudo().equals("(")){
			tokenOK();
		List<VariableDeclaration> variableDeclarations = idDeclList();
		if(variableDeclarations==null){
			throw new SyntaticErrorException("There are no variable declarations.",indexToken);
		}
		actualScope.addAllParameterList(variableDeclarations);
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals(")")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"process\"!!", indexToken);
			throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		}
		currentToken();
		if(actualToken==null ||!actualToken.getConteudo().equals(":")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \":\"!!", indexToken);
			throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}

		tokenOK();
		if(!func())return false; 
		currentToken();
		if(actualToken==null ||!actualToken.getConteudo().equals(":=")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \":=\"!!", indexToken);
			throw new SyntaticErrorException("Need a \":=\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		
		//trecho modificado

		BehaviourExpression behaviourExpression = B();
		if(behaviourExpression == null)
			throw new SyntaticErrorException("There are no correct behaviours", indexToken);
		
		actualScope.setBehaviourExpression(behaviourExpression);
		
		hideGatesIn(actualScope.getBehaviourExpression(),null);
		
		//remover lotosOperationCollection
				
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a \"where\", " +
						"or a \"endspec\"!!", indexToken);
		else if(actualToken.getConteudo().equals("where")){
			tokenOK();
			
			
			typeD();//deve ser tratado interno por ser opcional
						
			
			currentToken();
			if(actualToken==null){
				throw new SyntaticErrorException("Interrupted specification, specting a process definition!!", indexToken);
			}else if(actualToken.getConteudo().equals("process"))
				if(!P())return false;
			//retornar a coleção de operadores pai
			
		}
		currentToken();
		if(actualToken==null ||!actualToken.getConteudo().equals("endproc")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"endproc\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"endproc\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
		actualScope.getDadScope().addProcessDefinition(processDefinition);
	actualScope = actualScope.getDadScope();
		
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \"endspec\", or " +
					"another process definition!!", indexToken);
		}else if(actualToken.getConteudo().equals("process")){
			if(!P())return false;
		}
		
		
		return true;
	}
	
	private void hideGatesIn(BehaviourExpression behaviourExpression, List<Gate> hiddenGates) {
		// TODO Auto-generated method stub
		
		if(behaviourExpression != null){
			
			if(hiddenGates != null){
				behaviourExpression.addAllHiddenGates(hiddenGates);
			}
			
			hideGatesIn(behaviourExpression.getLeftBehaviourExpression(),behaviourExpression.getHiddenGates());
			hideGatesIn(behaviourExpression.getRightBehaviourExpression(),behaviourExpression.getHiddenGates());
			
		}
		
	}

	public boolean decl() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null ||!actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		
		//define the titule of the scope ... specification or process
		actualScope.setTitule(actualToken);

		
		tokenOK();
		currentToken();
		if(actualToken == null || !actualToken.getConteudo().equals("[")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"[\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"[\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		List<Gate> gList = gList();
		if(gList == null)return false;
		
		actualScope.setVisibleGates(gList);
		
		//Após adicionar os gates, deve-se verificar se existem gates a sincronizar
		//Add a gate to the scope definition
		//Gate gate = ExternalEnvironment.installGateOnEnnvironment(actualToken);
		//actualScope.addGate(gate);
		
		currentToken();
		if(actualToken == null || !actualToken.getConteudo().equals("]")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \"]\"!!", indexToken);
			throw new SyntaticErrorException("Need a \"]\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		
		return true;
	}
	
	//B -> behaviour expression
	public BehaviourExpression B() throws SyntaticErrorException{
		
		BehaviourExpression behaviourExpression = null;
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a behavioural expression!!", indexToken);
		}else
		if(actualToken.getConteudo().equals("(")){
				tokenOK();
				
				behaviourExpression = new BehaviourExpression(actualScope,indexToken);
				behaviourExpression.setOperator(BehaviourExpression.PARENTHESIS);
				
				//trecho modificado
				BehaviourExpression behaviourExpression2 = B();
				if(behaviourExpression2 == null)
					throw new SyntaticErrorException("There are no valid Behaviour in parenthesis.", indexToken);
				
				behaviourExpression.setRightBehaviourExpression(behaviourExpression2);
				
				currentToken();
				if(actualToken==null ||!actualToken.getConteudo().equals(")")){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
					throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				}
				tokenOK();
		}else
			if(actualToken.getConteudo().equals("[")){
				
				behaviourExpression = new BehaviourExpression(actualScope,indexToken);
				behaviourExpression.setOperator(BehaviourExpression.GUARD);
				
				tokenOK();
				
				SelectPredicate selectPredicate = actionPredEnd();
				if(selectPredicate == null)
					throw new SyntaticErrorException("There are no valid predicates to do guard.", indexToken);
				
				behaviourExpression.setSelectPredicate(selectPredicate);
				
				currentToken();
				if(actualToken==null ||!actualToken.getConteudo().equals("]")){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
					throw new SyntaticErrorException("Need a \"]\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				}
				tokenOK();
				currentToken();
				if(actualToken==null ||!actualToken.getConteudo().equals("->")){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
					throw new SyntaticErrorException("Need a \"->\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				}
				tokenOK();
				
				//trecho modificado
				BehaviourExpression behaviourExpression2 = B();
				if(behaviourExpression2 == null)
					throw new SyntaticErrorException("There are no guarded behaviours.", indexToken);
				
				//tratar precedência
				behaviourExpression.setRightBehaviourExpression(behaviourExpression2);
				
		}else
		if(actualToken.getConteudo().equals("stop")){
			behaviourExpression = new BehaviourExpression(actualScope,indexToken);
			behaviourExpression.setOperator(BehaviourExpression.STOP);
			tokenOK();
		}else
		if(actualToken.getConteudo().equals("exit")){
			behaviourExpression = new Exit(actualScope, indexToken);
			tokenOK();
			currentToken();
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
			if(actualToken.getConteudo().equals("(")){
				tokenOK();
				List<OpTable> opTableList = exitParam(null,UNDEFINED_SORT, 0);
				if(opTableList == null) 
					throw new SyntaticErrorException("There are no valid exit params.", indexToken);
				((Exit)behaviourExpression).addAll(opTableList);
				currentToken();
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
				if(!actualToken.getConteudo().equals(")"))
					throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				tokenOK();
			}
		}else if(actualToken.getConteudo().equals("i")){
			actualToken.setTipo(SystemHandler.lotosBook.INTERNAL_EVENT);
			tokenOK();
			Gate hiddenGate = new Gate(actualToken, Gate.HIDEN_GATE);
			hiddenGate.setTokenId(indexToken);
			currentToken();
			if(actualToken.getConteudo().equals("[")){
				tokenOK();
				
				SelectPredicate selectPredicate = actionPredEnd();
				if(selectPredicate == null)throw new SyntaticErrorException("Need a selection predicate.", indexToken);
				
				hiddenGate.setSelectPredicate(selectPredicate);
				
				currentToken();
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
				if(!actualToken.getConteudo().equals("]"))
					throw new SyntaticErrorException("Need a \"]\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				tokenOK();
			}
			currentToken();
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
			if (!actualToken.getConteudo().equals(";")){
				throw new SyntaticErrorException("Need a \";\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			
				behaviourExpression = new BehaviourExpression(actualScope,indexToken);
				behaviourExpression.setOperator(BehaviourExpression.ACTION_PREFIX);
				
				tokenOK();
				
				
				Action action = new Action(hiddenGate);
				
				behaviourExpression.setPrefixedAction(action);
				
				
				//trecho modificado
				BehaviourExpression behaviourExpression2 = B();
				if(behaviourExpression2 == null)
					throw new SyntaticErrorException("There are no behaviour prefixed by the hidden gate.", indexToken);

				//trecho modificado
				behaviourExpression = getBehaviourByPrecedence(behaviourExpression,behaviourExpression2);
				
				behaviourExpression.setRightBehaviourExpression(behaviourExpression2);
		}else if(actualToken.getTipo().equals(SystemHandler.lotosBook.ID)&& !actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			actualToken.setTipo(SystemHandler.lotosBook.GATEID);
			Token procIdToken = actualToken;
			tokenOK();
			Gate gate = new Gate(actualToken);
			gate.setTokenId(indexToken);
			Gate offerGate = offer();
			if(offerGate == null){
				currentToken();
				if(actualToken==null){
					throw new SyntaticErrorException("Interrupted specification, specting a \";\", or a \"[\"!!", indexToken);
				}else if(actualToken.getConteudo().equals("[")){

						behaviourExpression = new BehaviourExpression(actualScope,indexToken-1);
						behaviourExpression.setOperator(BehaviourExpression.PROCESS_INSTANTIATION);
						behaviourExpression.setProcessNameToken(procIdToken);
						
						procIdToken.setTipo(SystemHandler.lotosBook.PROCID); //tem que definir o token anterior como do um processo.
						tokenOK();
						List<Gate> gList = gList();
						if(gList == null){
							throw new SyntaticErrorException("Need a gate list.", indexToken);
						}//já vem com o novo token
						behaviourExpression.addAllProcessGates(gList);
						
						currentToken();
						if(actualToken==null || !actualToken.getConteudo().equals("]")){
							if(actualToken==null)
								throw new SyntaticErrorException("Interrupted specification, specting a \"]\"!!", indexToken);
							throw new SyntaticErrorException("Need a \"]\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
						}
						tokenOK();
						currentToken();
						if(actualToken.getConteudo().equals("(")){
							tokenOK();
						List <OpTable> parsingOpTables = dataExpList(null,UNDEFINED_SORT, 0);//deve ser tratado interno por ser opcional
						if(parsingOpTables != null){
							behaviourExpression.addAllParsingOpTables(parsingOpTables);
						}
						
						currentToken();
						if(actualToken==null || !actualToken.getConteudo().equals(")")){
							if(actualToken==null)
								throw new SyntaticErrorException("Interrupted specification, specting a \"process\"!!", indexToken);
							throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
						}
						tokenOK();
						}
				}else{
					currentToken();
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
					if (!actualToken.getConteudo().equals(";")){
						throw new SyntaticErrorException("Need a \";\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
					}
					

					behaviourExpression = new BehaviourExpression(actualScope,indexToken);
					behaviourExpression.setOperator(BehaviourExpression.ACTION_PREFIX);
											
						tokenOK();
						behaviourExpression.setPrefixedAction(new Action(gate));
						
						
						//trecho modificado
						BehaviourExpression behaviourExpression2 = B();
						if(behaviourExpression2 == null)
							throw new SyntaticErrorException("There are no behaviour prefixed by the gate \""+gate.getGateToken().getConteudo()+"\".", indexToken);
						

						behaviourExpression = getBehaviourByPrecedence(behaviourExpression,behaviourExpression2);
											
				}
			}else{
				offerGate.setTokenId(indexToken);
				Action action = new Action(offerGate);
				
				
				currentToken();
				if(actualToken.getConteudo().equals("[")){
					tokenOK();
					
					//trecho modificado
					SelectPredicate selectPredicate = actionPredEnd();
					if(selectPredicate == null)throw new SyntaticErrorException("Need a selection predicate", indexToken);
					
					action.setSelectPredicate(selectPredicate);

					currentToken();
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
					if(!actualToken.getConteudo().equals("]"))
						throw new SyntaticErrorException("Need a \"]\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
					tokenOK();
					
				}
				currentToken();
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \";\"!!", indexToken);
				if (!actualToken.getConteudo().equals(";")){
					throw new SyntaticErrorException("Need a \";\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
				}
							

				behaviourExpression = new BehaviourExpression(actualScope,indexToken);
				behaviourExpression.setOperator(BehaviourExpression.ACTION_PREFIX);
					tokenOK();
				
					
					behaviourExpression.setPrefixedAction(action);
					
					//trecho modificado
					BehaviourExpression behaviourExpression2 = B();
					if(behaviourExpression2 == null)
						throw new SyntaticErrorException("There are no behaviour prefixed by the gate \""+gate.getGateToken().getConteudo()+"\".", indexToken);
					

					behaviourExpression = getBehaviourByPrecedence(behaviourExpression,behaviourExpression2);
					
			}
		}else			
		if(actualToken.getConteudo().equals("hide")){
			
			behaviourExpression = new BehaviourExpression(actualScope,indexToken);
			behaviourExpression.setOperator(BehaviourExpression.HIDING);
			
			tokenOK();
			List<Gate> gList = gList();
			if(gList == null){
				throw new SyntaticErrorException("There are no defined hidden gates.", indexToken);
			}
			
			behaviourExpression.addAllHiddenGates(gList);
			
			currentToken();
			if(actualToken==null || !actualToken.getConteudo().equals("in")){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification, specting a \"in\"!!", indexToken);
				throw new SyntaticErrorException("Need a \"in\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no behaviour where include defined hidden gates.", indexToken);
			
			

			behaviourExpression = getBehaviourByPrecedence(behaviourExpression,behaviourExpression2);
			
		}else{
			throw new SyntaticErrorException("Need a initial behaviour token, like {\"stop\", \"exit\", \"i\", <IDENTIFIERS>, \"hide\"}, and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a operator!!", indexToken);
		}else
		if(actualToken.getConteudo().equals("[]")){

			BehaviourExpression choiceBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();
			
			
			choiceBehaviourExpression.setOperator(BehaviourExpression.CHOICE);
			
			choiceBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid alternative behaviour.", indexToken);
			

			choiceBehaviourExpression = getBehaviourByPrecedence(choiceBehaviourExpression,behaviourExpression2);
			
			return choiceBehaviourExpression;
			
		}else
		if(actualToken.getConteudo().equals("|[")){
			BehaviourExpression partialSynchBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();
			
			partialSynchBehaviourExpression.setOperator(BehaviourExpression.PARALLEL_COMPOSITION);
			partialSynchBehaviourExpression.setParallelCompositionType(BehaviourExpression.PARTIAL_SYNC);
			
			partialSynchBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			List<Gate> gList = gList();
			if(gList == null){
				throw new SyntaticErrorException("There are no valid gates where synchronize.", indexToken);
			}
			partialSynchBehaviourExpression.addAllSyncEventGates(gList);			
			
			currentToken();
			if(actualToken==null || !actualToken.getConteudo().equals("]|")){
				if(actualToken==null)
					throw new SyntaticErrorException("Interrupted specification!!", indexToken);
				throw new SyntaticErrorException("Need a \"]|\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			}
			tokenOK();
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid partally synchronous behaviour.", indexToken);
			
			

			partialSynchBehaviourExpression = getBehaviourByPrecedence(partialSynchBehaviourExpression,behaviourExpression2);
			
			return partialSynchBehaviourExpression;
			
		}else
		if(actualToken.getConteudo().equals("|||")){
			BehaviourExpression parallelBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();	
			
			
			parallelBehaviourExpression.setOperator(BehaviourExpression.PARALLEL_COMPOSITION);
			parallelBehaviourExpression.setParallelCompositionType(BehaviourExpression.PARALLEL);
			parallelBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid parallel behaviour.", indexToken);
			
			parallelBehaviourExpression = getBehaviourByPrecedence(parallelBehaviourExpression,behaviourExpression2);
			
			return parallelBehaviourExpression;
			
		}else
		if(actualToken.getConteudo().equals("||")){
			BehaviourExpression fullSyncBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();
			
			
			fullSyncBehaviourExpression.setOperator(BehaviourExpression.PARALLEL_COMPOSITION);
			fullSyncBehaviourExpression.setParallelCompositionType(BehaviourExpression.FULL_SYNC);
			
			fullSyncBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid totally synchronous behaviour.", indexToken);
			
			fullSyncBehaviourExpression = getBehaviourByPrecedence(fullSyncBehaviourExpression,behaviourExpression2);
			
			return fullSyncBehaviourExpression;
			
		}else
		if(actualToken.getConteudo().equals("[>")){
			BehaviourExpression disableBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();
			
			disableBehaviourExpression.setOperator(BehaviourExpression.DISABLING);
			
			disableBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid disabled behaviour.", indexToken);
			
			disableBehaviourExpression = getBehaviourByPrecedence(disableBehaviourExpression,behaviourExpression2);
			
			
			return disableBehaviourExpression;
			
		}else
		if(actualToken.getConteudo().equals(">>")){
			BehaviourExpression enableBehaviourExpression = new BehaviourExpression(actualScope,indexToken);
			tokenOK();
			
			enableBehaviourExpression.setOperator(BehaviourExpression.ENABLING);
			
			enableBehaviourExpression.setLeftBehaviourExpression(behaviourExpression);
			
			//trecho modificado
			BehaviourExpression behaviourExpression2 = B();
			if(behaviourExpression2 == null)
				throw new SyntaticErrorException("There are no valid enabled behaviour.", indexToken);
			
			enableBehaviourExpression = getBehaviourByPrecedence(enableBehaviourExpression,behaviourExpression2);
			
			return enableBehaviourExpression;
			
		}else{
			
		}
		
		return behaviourExpression;
	}
	
	private BehaviourExpression getBehaviourByPrecedence(
			BehaviourExpression behaviourExpression,
			BehaviourExpression behaviourExpression2) {
		
		BehaviourExpression behaviourByPrecedence = null;
		
		if(behaviourExpression2 != null){
		
		if(behaviourExpression.getOperator()<behaviourExpression2.getOperator() && behaviourExpression2.getLeftBehaviourExpression() != null){
			
			//behaviourExpression.setRightBehaviourExpression(behaviourExpression2.getLeftBehaviourExpression());
			
			behaviourExpression = getBehaviourByPrecedence(behaviourExpression, behaviourExpression2.getLeftBehaviourExpression());
			
			behaviourExpression2.setLeftBehaviourExpression(behaviourExpression);
			
			behaviourByPrecedence = behaviourExpression2;
			
		}else{
			
			behaviourExpression.setRightBehaviourExpression(behaviourExpression2);
			
			behaviourByPrecedence = behaviourExpression;
			
		}
		
		}else{
			behaviourByPrecedence = behaviourExpression;
		}
		
		return behaviourByPrecedence;
	}

	public  final String UNDEFINED_SORT = "UNDEFINED";
	
	public EventGate offer() throws SyntaticErrorException{
		EventGate eventGate = new EventGate(actualToken);
		eventGate.setTokenId(indexToken);
		currentToken();
		if(actualToken.getConteudo().equals("!")){
			ValueDeclaration valueDeclaration = rendezvousOutput();
			if(valueDeclaration == null)
			return null;
			
			eventGate.add(valueDeclaration);
			
			
		}else if(actualToken.getConteudo().equals("?")){
			VariableDeclaration variableDeclaration = rendezvousInput();
			if(variableDeclaration == null)
			return null;
			
			eventGate.add(variableDeclaration);
		}else{
			return null;
		}
		
		EventGate eventGate2 = offer();
		if(eventGate2 == null)return eventGate;//O não possui um novo offer
		eventGate2.setTokenId(indexToken);
		if(eventGate2.getOffers() != null && eventGate2.getOffers().size() > 0){
			eventGate.addAllValueDeclarations(eventGate2.getOffers());
		}
		if(eventGate2.getOffers() != null && eventGate2.getOffers().size() > 0){
			eventGate.addAllVariableDeclarations(eventGate2.getOffers());
		}
		
		return eventGate;
	}
	
	private VariableDeclaration rendezvousInput() throws SyntaticErrorException{
		
			tokenOK();
			VariableDeclaration variableDeclaration = idDecl();
			if(variableDeclaration == null){
				throw new SyntaticErrorException("Need a variable declaration where input!",indexToken);
			}
			return variableDeclaration;
	}
	
	private ValueDeclaration rendezvousOutput() throws SyntaticErrorException{
		
			tokenOK();
			//if(!dataExp())throw new SyntaticErrorException("There are no data expression to exchange.", indexToken);	
			OpTable opTable = dataExp(UNDEFINED_SORT, false);
			if(opTable == null){
				throw new SyntaticErrorException("Need a data expression to output!",indexToken);
			}
			ValueDeclaration valueDeclaration = new ValueDeclaration();
			valueDeclaration.setValueDeclarationTable(opTable);
			
			DataValue dataValue = extractDataValue(opTable);
			
			return valueDeclaration;
	}
	
	private DataValue extractDataValue(OpTable opTable) {
		// TODO Auto-generated method stub
		
		String sortOfDataExpression = opTable.getOpReturnSort();
		
		return null;
	}

	public OpTable dataExp(String requiredSortOfDataExp, boolean useRequiredSortForSelectDataSort) throws SyntaticErrorException{
		
		//OperationTable dataExpressionOperationTable = null;
		OpTable dataExpressionOpTable = null;
		
		
		currentToken();
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification!!", indexToken);
		
		//OperationTableRow actualOperationTableRow = new OperationTableRow();
		//this.actualOperationTableRow = actualOperationTableRow;
				
		if(actualToken.getConteudo().equals("(")){
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification!!", indexToken);
		
			tokenOK();
			
			OpTable opTable = null;
			
			if(useRequiredSortForSelectDataSort){
				opTable = dataExp(requiredSortOfDataExp, false);
			}else{
				opTable = dataExp(UNDEFINED_SORT, false);
			}
			
			if(opTable == null){
				throw new SyntaticErrorException("Need a data expression 5!",indexToken);
			}
			
			
			//retorna a linha to dataexpression
			currentToken();
			if(!actualToken.getConteudo().equals(")"))	throw new SyntaticErrorException("Need a \")\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
			tokenOK();
			currentToken();
			
			if(opTable != null){
				
				ConstantOp constantOp = new ConstantOp();
				constantOp.setOpTable(opTable);
				
				OpTable middleOpTable = validateMiddleDataExpression("PARENTHESES", opTable.getOpReturnSort());
					
					if(middleOpTable != null){
						
						for (int i = 0; i < middleOpTable.getOpList().size(); i++) {
							
							if(middleOpTable.getOpList().get(i) instanceof  LeftUnaryOp){
								((LeftUnaryOp)middleOpTable.getOpList().get(i)).setLeftOpTable(constantOp);
							}else{
								((BinaryOp)middleOpTable.getOpList().get(i)).setLeftOpTable(constantOp);
							}
							
						}
						
						dataExpressionOpTable = middleOpTable;
						
						indexToken = getLastIndexToken(middleOpTable);
						
						currentToken();
					}else{
						dataExpressionOpTable = opTable;
					}
			}
				
		}else {
			//Deve capturar um lista de operações possiveis
			dataExpressionOpTable = new OpTable(indexToken);
			
			OpTable firstOpTable = null;
			
			if(useRequiredSortForSelectDataSort){
				firstOpTable = validateDataExpression(requiredSortOfDataExp);
			}else{
				firstOpTable = validateDataExpression(UNDEFINED_SORT);				
			}
						
			if(firstOpTable != null){
				
				dataExpressionOpTable.setOpList(new ArrayList<Op>());
				
				for (int i = 0; i < firstOpTable.getOpList().size(); i++) {
					
					Op constantOrParameterizedOp = null;
					
					if(firstOpTable.getOpList().get(i) instanceof ConstantOp){
						constantOrParameterizedOp = new ConstantOp();
						constantOrParameterizedOp = (ConstantOp)firstOpTable.getOpList().get(i);
					}else if(firstOpTable.getOpList().get(i) instanceof ParameterizedOp){
						constantOrParameterizedOp = new ParameterizedOp();
						constantOrParameterizedOp = (ParameterizedOp)firstOpTable.getOpList().get(i);
					}
					
					indexToken = constantOrParameterizedOp.getIndexOfOp();
					
					currentToken();

					OpTable middleOpTable = null;
					try{
					
						middleOpTable = validateMiddleDataExpression((firstOpTable.getOpList().get(i)).getMainLotosOperation().operatorType, firstOpTable.getOpList().get(i).getReturnSortOfOperation());
					
					}catch (SyntaticErrorException e) {
						indexToken = constantOrParameterizedOp.getIndexOfOp();
					}finally{
						if(middleOpTable != null){
								
							for (int j = 0; j < middleOpTable.getOpList().size(); j++) {
								
								if(middleOpTable.getOpList().get(j) instanceof LeftUnaryOp){
									((LeftUnaryOp)middleOpTable.getOpList().get(j)).setLeftOpTable(constantOrParameterizedOp);
								}else{
									((BinaryOp)middleOpTable.getOpList().get(j)).setLeftOpTable(constantOrParameterizedOp);
								}
								
								dataExpressionOpTable.getOpList().add(middleOpTable.getOpList().get(j));
								
							}
							
							indexToken = getLastIndexToken(middleOpTable);
								
							currentToken();
						}else{
							dataExpressionOpTable.getOpList().add(constantOrParameterizedOp);
							indexToken = constantOrParameterizedOp.getIndexOfOp();
						}
					}
				} 
			}else{
				
				OpTable middleOpTable = null;
				
				middleOpTable = validateMiddleDataExpression(NOT_FOUND_OP, NOT_FOUND_OP);
			
				dataExpressionOpTable = middleOpTable;
				
			}
				if(dataExpressionOpTable == null || dataExpressionOpTable.getOpList() == null || dataExpressionOpTable.getOpList().size() == 0) return null;
		}
				
		return dataExpressionOpTable;
	}
	
	
	private int getLastIndexToken(OpTable middleOpTable) {
		
		boolean flag = true;
		
		Op op = middleOpTable.getOpList().get(0);
		
		while(flag){
			if(op instanceof BinaryOp){ 
				op = ((BinaryOp)op).getRightOpTable();
			}else if(op instanceof RightUnaryOp){
				op = ((RightUnaryOp)op).getRightOpTable();
			}else{
				return op.getIndexOfOp();
			}
			
		}
		
		return 0;
	}

	private OpTable validateMiddleDataExpression(String firstOperatorType, String firstOperatorSort) throws SyntaticErrorException{
		currentToken();
		
		int inicialTokenOfDataExpression = indexToken;
				
		OpTable middleLotosOpTable = getMiddleOperationOfCurrenToken(actualScope,0, firstOperatorType, firstOperatorSort);
		
		if(middleLotosOpTable == null || middleLotosOpTable.getOpList() == null || middleLotosOpTable.getOpList().size() == 0){
			indexToken = inicialTokenOfDataExpression;
			return null;
		}
			
		
		return middleLotosOpTable;
	}
	
	private OpTable getMiddleOperationOfCurrenToken(Scope typeDefinitionScope, int typeDefinitionId, String firstOperatorType, String firstOperatorSort) throws SyntaticErrorException{
		currentToken();
		
		OpTable middleLotosOpTable = null;
		
		if(typeDefinitionScope == null)return null;
		else middleLotosOpTable = new OpTable(indexToken);
				
			
		middleLotosOpTable.setOpList(new ArrayList<Op>());
		
		if(typeDefinitionScope.getTypeDefitions() != null && typeDefinitionScope.getTypeDefitions().size()>typeDefinitionId &&  typeDefinitionScope.getTypeDefitions().get(typeDefinitionId).getOperatorCollection().possibleOperations != null){
		
			
		OperatorCollection operatorCollection = typeDefinitionScope.getTypeDefitions().get(typeDefinitionId).getOperatorCollection();
		for (LotosOperation lotosOperation : operatorCollection.possibleOperations) {
			
			if(firstOperatorType.equals(NOT_FOUND_OP)){
				if(useRightOperator(lotosOperation)){
						int operatorInitId = indexToken;
						tokenOK();
	
						OpTable opTable = dataExp(lotosOperation.lotosSorts.get(0), true);
						if(opTable == null || opTable.getOpReturnSort() != lotosOperation.lotosSorts.get(0)){
							indexToken = operatorInitId;
							currentToken();
						}else{
						
							RightUnaryOp rightUnaryOp = new RightUnaryOp();
							rightUnaryOp.setMainLotosOperation(lotosOperation);
							
							ConstantOp constantOp = new ConstantOp();
							constantOp.setOpTable(opTable);
							constantOp.setIndexOfOp(indexToken);
							rightUnaryOp.setRightOpTable(constantOp);
							
							middleLotosOpTable.getOpList().add(rightUnaryOp);
							
							indexToken = operatorInitId;
							currentToken();
						}
						
						
				}
				
			}else{
				if(useLeftOperator(lotosOperation, firstOperatorSort)){
						int operatorInitId = indexToken;
						tokenOK();
						OpTable operationTable = dataExp(UNDEFINED_SORT, false);
						if(operationTable != null){
							indexToken = operatorInitId;
							currentToken();
						}else{
							LeftUnaryOp leftUnaryOp = new LeftUnaryOp();
							leftUnaryOp.setMainLotosOperation(lotosOperation);
							leftUnaryOp.setIndexOfOp(operatorInitId+1);
							middleLotosOpTable.getOpList().add(leftUnaryOp);
							indexToken = operatorInitId;
							currentToken();
						}
				}
				if(useLeftRightOperator(lotosOperation, firstOperatorSort)){
					int operatorInitId = indexToken;
					tokenOK();
					OpTable opTable = dataExp(lotosOperation.lotosSorts.get(1), true);
					if(opTable == null || !opTable.getOpReturnSort().equals(lotosOperation.lotosSorts.get(0))){
						indexToken = operatorInitId;
						currentToken();
					}else{
						BinaryOp binaryOp = new BinaryOp();
						binaryOp.setMainLotosOperation(lotosOperation);
						
						ConstantOp constantOp = new ConstantOp();
						constantOp.setOpTable(opTable);
						constantOp.setIndexOfOp(indexToken);
						binaryOp.setRightOpTable(constantOp);
						
						middleLotosOpTable.getOpList().add(binaryOp);
						indexToken = operatorInitId;
						currentToken();
					}
					
				}
				
			}
			
		}
		
		}
		
		
		OpTable dadOpTable  = null;
		
		if(typeDefinitionScope.getTypeDefitions() == null || typeDefinitionScope.getTypeDefitions().size()<=typeDefinitionId){
		   
			if((typeDefinitionScope instanceof Specification) && ((Specification)typeDefinitionScope).getFlagInnerOrGlobalTypeDefinition()){
				((Specification)typeDefinitionScope).setFlagInnerOrGlobalTypeDefinition(false);
				dadOpTable = getMiddleOperationOfCurrenToken(typeDefinitionScope,0, firstOperatorType, firstOperatorSort);
			}else{
				dadOpTable = getMiddleOperationOfCurrenToken(typeDefinitionScope.getDadScope(),0, firstOperatorType, firstOperatorSort);
			}
			
		}else{
			dadOpTable = getMiddleOperationOfCurrenToken(typeDefinitionScope, typeDefinitionId+1 , firstOperatorType, firstOperatorSort);
		}
		
		if(dadOpTable != null)
			middleLotosOpTable.getOpList().addAll(dadOpTable.getOpList());
		
		if(middleLotosOpTable ==  null || middleLotosOpTable.getOpList() == null){
			return null;
		}else{
			return middleLotosOpTable;
		}
	}

	private boolean useLeftRightOperator(LotosOperation lotosOperation2, String firstOperationSort) {
		if(actualToken.getConteudo().equals(lotosOperation2.operador) && lotosOperation2.operatorType.equals(LotosOperation.LEFT_AND_RIGHT_OPERAND) && lotosOperation2.lotosSorts.get(0).equals(firstOperationSort)){
			return true;
		}
		
		return false;
	}

	private boolean useLeftOperator(LotosOperation lotosOperation2, String firstOperationSort) {
		if(actualToken.getConteudo().equals(lotosOperation2.operador) && lotosOperation2.operatorType.equals(LotosOperation.LEFT_OPERAND) && lotosOperation2.lotosSorts.get(0).equals(firstOperationSort)){
			return true;
		}
		return false;
	}

	private boolean useRightOperator(LotosOperation lotosOperation2) {
		if(actualToken.getConteudo().equals(lotosOperation2.operador) &&  lotosOperation2.operatorType.equals(LotosOperation.RIGHT_OPERAND)){
			return true;
		}
		return false;
	}

	private OpTable validateDataExpression(String requiredSortOfOperation) throws SyntaticErrorException{
		currentToken();
		
		List<Op> dataExpressionOpConstants = null;
		
		int inicialTokenOfDataExpression = indexToken;
		
		LotosOperation lotosOperation = null;
		
		lotosOperation = getSimpleDeclaration(requiredSortOfOperation);
		
		if(lotosOperation == null)
			dataExpressionOpConstants = getOperationOfCurrenToken(actualScope,0, requiredSortOfOperation);
		else{
			dataExpressionOpConstants = new ArrayList<Op>();
			ConstantOp constantOp = new ConstantOp();
			constantOp.setMainLotosOperation(lotosOperation);
			constantOp.setIndexOfOp(indexToken);
			//dataExpressionOpConstants.add(new LotosOperationItem(lotosOperation, indexToken));
			dataExpressionOpConstants.add(constantOp);
		}
	
		if(dataExpressionOpConstants == null || dataExpressionOpConstants.size() == 0){
			 indexToken = inicialTokenOfDataExpression;
			 return null;
		}
		
		OpTable constantsOpTable = new OpTable(indexToken);
		constantsOpTable.setOpList(new ArrayList<Op>());
		constantsOpTable.getOpList().addAll(dataExpressionOpConstants);
		
		return constantsOpTable;
		
	}

	private LotosOperation getSimpleDeclaration(String requiredSortOfOperation)throws SyntaticErrorException {
		int index = indexToken;
		
		currentToken();
		
		
		if(actualToken == null)
			throw new SyntaticErrorException("Interrupted specification!!", indexToken);
		if((actualToken.getTipo().equals(SystemHandler.lotosBook.ID)|| (actualToken.getTipo().equals(SystemHandler.lotosBook.OPERATOR))||(actualToken.getTipo().equals(SystemHandler.lotosBook.OPERAND)))&& !actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			actualToken.setTipo(SystemHandler.lotosBook.OPERATOR_KEY);
			String opOfaSort = actualToken.getConteudo();
			tokenOK();
			currentToken();
			if(actualToken.getConteudo().equals("of")){
				tokenOK();
				currentToken();
				if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
					throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token, a SORT," +
							"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
							"\n\t", indexToken);
				}
				if(!actualToken.getConteudo().equals(requiredSortOfOperation) && !requiredSortOfOperation.equals(UNDEFINED_SORT)){
					throw new SyntaticErrorException("The sort,'"+actualToken.getConteudo()+"' , is diferent of the required, '"+requiredSortOfOperation+"'.", indexToken);
				}
				tokenOK();
		
				return seachLotosOperationOfaSort(opOfaSort,actualToken.getConteudo(),actualScope);
			}
		
		}
		
		
		indexToken = index;
		currentToken();
		return null;
	}

	private LotosOperation seachLotosOperationOfaSort(String opOfaSort,
			String conteudo, Scope scope) {
		
		if(scope != null){
		
		if(scope.getScopeSorts() != null){
		
			for (int i = 0; i < scope.getScopeSorts().size(); i++) {
				if(scope.getScopeSorts().get(i).getSortName().equalsIgnoreCase(conteudo)){
					
					TypeDefinition typeDefinition = scope.getScopeSorts().get(i).getTypeDefinitionReference();
				
					if(typeDefinition != null){
						
						OperatorCollection operatorCollection = typeDefinition.getOperatorCollection();
						
						if(operatorCollection != null){
							
							for (int j = 0; j < operatorCollection.getPossibleOperations().size(); j++) {
								
								if(operatorCollection.getPossibleOperations().get(j).operador.equals(opOfaSort)){
									if(operatorCollection.getPossibleOperations().get(j).returnSort.equalsIgnoreCase(conteudo)){
										return operatorCollection.getPossibleOperations().get(j);
									}
									break;
								}
								
							}
							
						}
						
					}
					
				}
			}
			
		}
		
		if(scope instanceof Specification){
			if(((Specification)scope).getFlagInnerOrGlobalTypeDefinition()){
				((Specification)scope).setFlagInnerOrGlobalTypeDefinition(false);
				return seachLotosOperationOfaSort(opOfaSort,
						conteudo, scope);
			}
		}
		
		return seachLotosOperationOfaSort(opOfaSort,
				conteudo, scope.getDadScope());
				
		}
		
		return null;
	}

	private List<Op> getOperationOfCurrenToken(Scope typeDefinitionScope, int typeDefinitionId, String requiredSortOfOperation) throws SyntaticErrorException{
		
		currentToken();
		
		List<Op> constantsOpDataExpressions = null;
		
		
		if(typeDefinitionScope == null)return null;
		else constantsOpDataExpressions = new ArrayList<Op>();
				
		if(typeDefinitionScope.getTypeDefitions() != null && typeDefinitionScope.getTypeDefitions().size()>typeDefinitionId &&  typeDefinitionScope.getTypeDefitions().get(typeDefinitionId).getOperatorCollection().possibleOperations != null){
		
			OperatorCollection operatorCollection = typeDefinitionScope.getTypeDefitions().get(typeDefinitionId).getOperatorCollection();
			for (LotosOperation lotosOperation : operatorCollection.possibleOperations) {
			
			if(equalsOperator(lotosOperation, requiredSortOfOperation)){
				if(lotosOperation.operatorType.equals(LotosOperation.PARAMETER_OPERAND)){
					int operatorInitiId = indexToken;
					tokenOK();
					List<OpTable> parametersOpTables = checkParameterOperator(lotosOperation.lotosSorts);
					if(parametersOpTables != null && parametersOpTables.size() > 0 && parametersOpTables.size()==lotosOperation.numberOfArguments){
				
						ParameterizedOp parameterizedOp = new ParameterizedOp();
						parameterizedOp.setMainLotosOperation(lotosOperation);
						parameterizedOp.setIndexOfOp(indexToken);
						parameterizedOp.setParametersOpTables(parametersOpTables);
						constantsOpDataExpressions.add(parameterizedOp);
						
						indexToken = operatorInitiId;
						currentToken();
					}else{
						indexToken = operatorInitiId;
						currentToken();
					}
				}else{
					int operatorInitiId = indexToken;
					tokenOK();
					List <OpTable> parametersOpTables = checkParameterOperator(null);
					if(parametersOpTables == null){
						indexToken = operatorInitiId;
						currentToken();
						tokenOK();
						ConstantOp constantOp = new ConstantOp();
						constantOp.setMainLotosOperation(lotosOperation);
						constantOp.setIndexOfOp(indexToken);
						constantsOpDataExpressions.add(constantOp);
						indexToken = operatorInitiId;
						currentToken();
					}else{
						indexToken = operatorInitiId;
						currentToken();
					}
				}
			}			
		}
		}
		
		List<Op> constantOpsDad = null;
		
		if(typeDefinitionScope.getTypeDefitions() == null || typeDefinitionScope.getTypeDefitions().size()<=typeDefinitionId){
		   
			if((typeDefinitionScope instanceof Specification) && ((Specification)typeDefinitionScope).getFlagInnerOrGlobalTypeDefinition()){
				((Specification)typeDefinitionScope).setFlagInnerOrGlobalTypeDefinition(false);
				constantOpsDad = getOperationOfCurrenToken(typeDefinitionScope,0, requiredSortOfOperation);
			}else{
				constantOpsDad = getOperationOfCurrenToken(typeDefinitionScope.getDadScope(),0, requiredSortOfOperation);
			}
			
		}else{
			constantOpsDad = getOperationOfCurrenToken(typeDefinitionScope, typeDefinitionId+1 , requiredSortOfOperation);
		}
			
		
		if(constantOpsDad != null)
			constantsOpDataExpressions.addAll(constantOpsDad);
			
		
		if(constantsOpDataExpressions ==  null || constantsOpDataExpressions.size() == 0){
				return null;
		}else{
			return constantsOpDataExpressions;
		}
	}

	
	private List<OpTable> checkParameterOperator(List<String> requiredArgumentSorts) throws SyntaticErrorException{
		currentToken();
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification!!", indexToken);
		if(!actualToken.getConteudo().equals("("))
			return null;
		tokenOK();
		
		List <OpTable> parametersOpTables = new ArrayList<OpTable>();
		
		try{
			parametersOpTables = dataExpList(requiredArgumentSorts, UNDEFINED_SORT, 0);
		}catch(SyntaticErrorException see){
			return null;
		}
					
		currentToken();
		if(!actualToken.getConteudo().equals(")"))
			throw new SyntaticErrorException("1 You need to close the the parenthesis with ')'!", indexToken);
		tokenOK();
		
		return parametersOpTables;
	}
	
	private boolean equalsOperator(LotosOperation lotosOperation2, String requiredSortOfOperation) {
		
		if(actualToken.getConteudo().equals(lotosOperation2.operador) && (lotosOperation2.returnSort.equals(requiredSortOfOperation) || requiredSortOfOperation.equals(UNDEFINED_SORT) ) && (lotosOperation2.operatorType.equals(LotosOperation.SINGLE_OPERAND) || lotosOperation2.operatorType.equals(LotosOperation.PARAMETER_OPERAND))){
			return true;
		}
		
		return false;
	}

	public List<VariableDeclaration> idDeclList() throws SyntaticErrorException{
		
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		}
		VariableDeclaration variableDeclaration = idDecl();
		if(variableDeclaration == null)
			throw new SyntaticErrorException("There are no data expression after \"(\".", indexToken);
		
		List<VariableDeclaration> variableDeclarationList = new ArrayList<VariableDeclaration>();
		variableDeclarationList.add(variableDeclaration);
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			
			variableDeclarationList.addAll(idDeclList());
		}
		return variableDeclarationList;
	}
	
	public VariableDeclaration idDecl()throws SyntaticErrorException{
		currentToken();
		LotosOperation lotosOperation = new LotosOperation();
		VariableDeclaration variableDeclaration = null;
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		variableDeclaration = new VariableDeclaration();
		variableDeclaration.setVariable(actualToken,indexToken);
		actualToken.setTipo(SystemHandler.lotosBook.VARID);
		lotosOperation.addToken(actualToken.getConteudo(), indexToken);
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getConteudo().equals(":")){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification!!", indexToken);
			throw new SyntaticErrorException("Need a \":\", and the given token is a \""+currentToken().getConteudo()+"\"!!\n\t",indexToken);
		}
		tokenOK();
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
					"\n\t", indexToken);
		}
		Sort sort =  publishSort(actualScope, actualToken.getConteudo());
		
		if(sort == null){
			throw new SyntaticErrorException("There are no sorts with the given name.", indexToken);
		}
		
		variableDeclaration.setSort(sort,indexToken);
		actualToken.setTipo(SystemHandler.lotosBook.SORTTD);
		lotosOperation.setReturnSort(actualToken.getConteudo());
		actualOperatorCollection.addOperation(lotosOperation);
		tokenOK();
		currentToken();
		return variableDeclaration;
	}
	
	public List<Gate> gList() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		
		List<Gate> gateList = new ArrayList<Gate>();
		Gate gate = new Gate(actualToken);
		gate.setTokenId(indexToken);
		gateList.add(gate);
		
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			List<Gate> gList = gList();
			if(gList == null || gList.size() == 0)return null;
			gateList.addAll(gList);
		}
		return gateList;
		
	}
	
	public List<TypeDefinition> typeDefinitionExtendingList() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		String type = actualToken.getConteudo();
		List<TypeDefinition> extendingTydefinitionList = new ArrayList<TypeDefinition>();
		TypeDefinition extendingTypeDefinition = searchTypeDefinitionDeclaration(actualToken, actualScope);
		
		if(extendingTypeDefinition == null){
			throw new SyntaticErrorException("This type definition ('"+type+"') was not declared previously!", indexToken);
		}
		
		extendingTydefinitionList.add(extendingTypeDefinition);
		
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			List<TypeDefinition> typeDefinitionList = typeDefinitionExtendingList();
			if(typeDefinitionList == null || typeDefinitionList.size() == 0)return null;
			extendingTydefinitionList.addAll(typeDefinitionList);
		}
		return extendingTydefinitionList;
		
	}

	private TypeDefinition searchTypeDefinitionDeclaration(Token actualToken2, Scope seachScope) {
		// TODO Auto-generated method stub
		
		if(seachScope == null)return null;
		
		
		List<TypeDefinition> typeDefinitionList = seachScope.getTypeDefitions();
				
		if(typeDefinitionList != null){
		
			for (int i = 0; i < typeDefinitionList.size(); i++) {
				if(actualToken2.getConteudo().equalsIgnoreCase(typeDefinitionList.get(i).getTypeTitule().getConteudo())){
					return typeDefinitionList.get(i);
				}
			}
		
		}
			
		if(actualScope instanceof Specification){
			if(((Specification)actualScope).getFlagInnerOrGlobalTypeDefinition()){
				((Specification)actualScope).setFlagInnerOrGlobalTypeDefinition(false);			
			    return searchTypeDefinitionDeclaration(actualToken2, seachScope);
			}
			//vai retornar null pois não tem uma declaração de tipo superior a essa
			return searchTypeDefinitionDeclaration(actualToken2, seachScope.getDadScope());
		}else{
			return searchTypeDefinitionDeclaration(actualToken2, seachScope.getDadScope());
		}
	}

	public List<OpTable> dataExpList(List<String> requiredArgumentSorts, String requiredSortOfList, int index) throws SyntaticErrorException{
		currentToken();
		List<OpTable> dataExpOpList = new ArrayList<OpTable>();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		}else{
			//if(!dataExp())throw new SyntaticErrorException("There are no data expression after \"(\".", indexToken);
			if(requiredArgumentSorts!=null){
				if(requiredArgumentSorts.size() > index){
					//retorna número errado de argumentos
					requiredSortOfList = requiredArgumentSorts.get(index);
				}else{
					throw new SyntaticErrorException("Need a data expression 6!"+currentToken().getConteudo(),indexToken);
				}
			}
			OpTable operationTable = dataExp(requiredSortOfList, false);
			if(operationTable == null){
				throw new SyntaticErrorException("Need a data expression 6!"+currentToken().getConteudo(),indexToken);
			}
			dataExpOpList.add(operationTable);
		}
		int numOfDataExp = 1;
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
				dataExpOpList.addAll(dataExpList(requiredArgumentSorts, requiredSortOfList, index+1));
		}
		return dataExpOpList;
		
	}
	
	public List<Token> libraryList() throws SyntaticErrorException{
		currentToken();
		if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
			if(actualToken==null)
				throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
			throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
					"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
						"\n\t", indexToken);
		}
		List<Token> libraryTokens = new ArrayList<Token>();
		try {
			libraryTokens.addAll(SystemHandler.getLibraryTokens(actualToken.getConteudo()));
		} catch (LexicalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		tokenOK();
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
			libraryTokens.addAll(libraryList());
		}
		return libraryTokens;
		
	}
	
	public List<OpTable> exitParam(List<String> requiredArgumentSorts, String requiredSortOfList, int index) throws SyntaticErrorException{
		currentToken();
		List<OpTable> dataExpOpList = new ArrayList<OpTable>();
		if(actualToken==null)
			throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
		if(actualToken.getConteudo().equals("any")){
				tokenOK();
				currentToken();
				if(actualToken==null || !actualToken.getTipo().equals(SystemHandler.lotosBook.ID)||actualToken.getTipo().equals(SystemHandler.lotosBook.KEYWORK)){
					if(actualToken==null)
						throw new SyntaticErrorException("Interrupted specification, specting a <IDENTIFIER>!!", indexToken);
					throw new SyntaticErrorException("Need a \""+SystemHandler.lotosBook.ID+"\" token," +
							"\n\t and the given token, \""+currentToken().getConteudo()+"\" is a \""+currentToken().getTipo()+"\" token!" +
								"\n\t", indexToken);
				}
				OpTable opTable = new OpTable(indexToken);
				opTable.setOpList(new ArrayList<Op>());
				opTable.getOpList().add(new AnyOp(actualToken));
				dataExpOpList.add(opTable);
				//Setando tipo semântico
				actualToken.setTipo(SystemHandler.lotosBook.SORTTD);
				tokenOK();
		}else{
			if(requiredArgumentSorts!=null){
				if(requiredArgumentSorts.size() > index){
					//retorna número errado de argumentos
					requiredSortOfList = requiredArgumentSorts.get(index);
				}else{
					throw new SyntaticErrorException("Need a data expression 6!"+currentToken().getConteudo(),indexToken);
				}
			}
			OpTable operationTable = dataExp(requiredSortOfList, false);
			if(operationTable == null){
				throw new SyntaticErrorException("Need a data expression 6!"+currentToken().getConteudo(),indexToken);
			}
			dataExpOpList.add(operationTable);
		}
		int numOfDataExp = 1;
		currentToken();
		if(actualToken==null){
			throw new SyntaticErrorException("Interrupted specification, specting a \",\"!!", indexToken);
		}else
		if(actualToken.getConteudo().equals(",")){
			tokenOK();
				dataExpOpList.addAll(exitParam(requiredArgumentSorts, requiredSortOfList, index+1));
		}
		return dataExpOpList;
		
	}
		
	public SelectPredicate actionPredEnd() throws SyntaticErrorException{
		
		currentToken();
		
		SelectPredicate selectPredicate = new SelectPredicate();
	
		
		
		OpTable opTable = dataExp(UNDEFINED_SORT, false);
		if(opTable == null){
			throw new SyntaticErrorException("Need a data expression 8!",indexToken);
		}
		
		selectPredicate.setLeftValueOpTable(opTable);
						
		currentToken();
		if(actualToken==null)throw new SyntaticErrorException("Interrupted specification, specting a type function!!!", indexToken);
		if (!actualToken.getConteudo().equals("=") && opTable.getOpReturnSort().equalsIgnoreCase("bool")){
				throw new SyntaticErrorException("Need a \"=\" 3, or the data expression must return a Bool sort!!\n\t",indexToken);
		}
		
		tokenOK();
	
		if(actualToken.getConteudo().equals("=")){
		
					
			OpTable opTable2 = dataExp(UNDEFINED_SORT, false);
			if(opTable2 == null){
				throw new SyntaticErrorException("There are no data expression to exchange.",indexToken);
			}
			
			selectPredicate.setRightValueOpTable(opTable2);
		
		}
		return selectPredicate;
	}
	
	
}