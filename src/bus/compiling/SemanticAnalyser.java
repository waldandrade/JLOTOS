package bus.compiling;

import java.util.ArrayList;
import java.util.List;

import sun.misc.Cleaner;

import bus.SystemHandler;
import bus.compiling.exceptions.PossibleDeadLock;
import bus.compiling.exceptions.SemanticErrorException;
import bus.compiling.exceptions.SemanticProblem;
import bus.compiling.semanticStructure.BehaviourExpression;
import bus.compiling.semanticStructure.Functionality;
import bus.compiling.semanticStructure.Gate;
import bus.compiling.semanticStructure.ProcessInstance;
import bus.compiling.semanticStructure.Scope;
import bus.compiling.semanticStructure.Specification;
import bus.compiling.semanticStructure.ProcessDefinition;
import bus.compiling.semanticStructure.syncronize.EventGate;
import bus.compiling.semanticStructure.syncronize.ExternalEnvironment;
import bus.compiling.semanticStructure.syncronize.VariableDeclaration;


public class SemanticAnalyser {
	
	public Specification specification = null;
	
	public Scope analyzingScope = null;
	
	public Scope globalScope = null;
	
	public Token actualToken = null;
		
	ArrayList<Token> codTokens = null;
	
	public int indexToken = 0;
	

	ArrayList<SemanticErrorException> processDublicateErrors = null;
	ArrayList<SemanticErrorException> gateDublicateErrors = null;
	ArrayList<SemanticErrorException> gateNotExistsErrors = null;
	ArrayList<SemanticErrorException> processAssignError = null;
	ArrayList<SemanticErrorException> processInstatiationAssignError = null;
	ArrayList<PossibleDeadLock> syncOfferPossibleDeadlock = null;
	
   
    private SemanticProblem semanticProblem = new SemanticProblem();
	
	public SemanticProblem analyse(Specification specification, ArrayList<Token> codTokens){
		
		this.specification = specification;
		this.codTokens = codTokens;
		
			initDown();
		
			
			
		return semanticProblem;
	}
	
	public void initDown(){
		//System.out.println(programa());
			
			processDublicateErrors = new ArrayList<SemanticErrorException>();
			gateDublicateErrors = new ArrayList<SemanticErrorException>();
			gateNotExistsErrors = new ArrayList<SemanticErrorException>();
			processAssignError = new ArrayList<SemanticErrorException>();
			processInstatiationAssignError = new ArrayList<SemanticErrorException>();
			syncOfferPossibleDeadlock = new ArrayList<PossibleDeadLock>();
			
			verifyProcesses(specification);
			verifyGateDuplicity(specification);
			verifyProcessAssign(specification);
			

			veriryBehaviourExpressions(specification);
			
			for(SemanticErrorException e : processDublicateErrors){
				
				semanticProblem.addSemanticErrorException(e);
								
			}
			
			for(SemanticErrorException e : gateDublicateErrors){

				semanticProblem.addSemanticErrorException(e);
				
			}
			
			for(SemanticErrorException e : gateNotExistsErrors){

				semanticProblem.addSemanticErrorException(e);
				
			}
			
			for(SemanticErrorException e : processAssignError){

				semanticProblem.addSemanticErrorException(e);
				
			}
			
			for(PossibleDeadLock e : syncOfferPossibleDeadlock){

				semanticProblem.addPossibleDeadLock(e);
				
			}
			
	}
	
	
	private void verifyProcessAssign(Scope globalScope2) {
		
		
	}

	private void veriryBehaviourExpressions(Scope scope) {
		
		if(scope != null){
		
		if(scope.getBehaviourExpression() != null){
	
			verifyBehaviourExpression(scope.getBehaviourExpression());			
		
		}
		
		if(scope.getProcessDefinitions() != null ){

			for (ProcessDefinition processDefinition: scope.getProcessDefinitions()) {
				
				veriryBehaviourExpressions(processDefinition);
				
			}
		}
		
		
		}
		
		
	}

	private void verifyBehaviourExpression(BehaviourExpression behaviourExpression) {
		if(behaviourExpression != null){
		if(behaviourExpression.getOperator() ==  BehaviourExpression.ACTION_PREFIX){
			checkActionPrefix(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.CHOICE){
			checkChoice(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.DISABLING){
			checkDisabling(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.PROCESS_INSTANTIATION){
			checkProcessInstantiation(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.ENABLING){
			checkEnabling(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.EXIT){
			checkExit(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.GUARD){
			checkGuard(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.HIDING){
			checkHiding(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.PARALLEL_COMPOSITION){
			checkParallelComposition(behaviourExpression);
		}else if(behaviourExpression.getOperator() ==  BehaviourExpression.STOP){
			checkActionPrefix(behaviourExpression);
		}
		
		verifyBehaviourExpression(behaviourExpression.getLeftBehaviourExpression());
		verifyBehaviourExpression(behaviourExpression.getRightBehaviourExpression());
		
		}
		
	}

	private void checkProcessInstantiation(
			BehaviourExpression behaviourExpression) {
		
		//verificar a existência do processo
		
		List<ProcessDefinition> processDefinitions = null;
		
		try{
		
		processDefinitions = chackProcessDefinitionExistence(behaviourExpression.getProcessNameToken(), behaviourExpression.getIndex() , behaviourExpression.getScope());
		
		}catch (SemanticErrorException e) {
			
			processAssignError.add(e);
			
		}
		
		if(behaviourExpression.getProcessGates() != null){
			
			
			for (int i = 0; i < behaviourExpression.getProcessGates().size(); i++) {	
				
				checkGateExistence(behaviourExpression.getProcessGates().get(i),behaviourExpression.getScope().getVisibleGates(),behaviourExpression.getHiddenGates());
				
			}
		
		}
		
		if(processDefinitions != null){
			
				try{
				processDefinitions = checkProcessParameters(processDefinitions, behaviourExpression.getParsingOpTables());
				}catch (SemanticErrorException e) {
					processAssignError.add(e);
				}
				
				if (processDefinitions.size() > 1){
					
					

					String wrongSortList = "";
					
					if(behaviourExpression.getParsingOpTables() != null && behaviourExpression.getParsingOpTables().size() > 0){
					
					wrongSortList += "( ";
					
					for (OpTable opTable : behaviourExpression.getParsingOpTables()) {
						
						if(!wrongSortList.equals("( ")){
							wrongSortList += " ,";
						}
						
						wrongSortList += opTable.getOpReturnSort();
						
					}
					
					wrongSortList += " )";
					
					}
					
					String wrongGateList = "";
					
					if(behaviourExpression.getProcessGates() != null && behaviourExpression.getProcessGates().size() > 0){
					
					wrongGateList += "[ ";
					
					for (Gate gate : behaviourExpression.getProcessGates()) {
						
						if(!wrongGateList.equals("[ ")){
							wrongGateList += " ,";
						}
						
						wrongGateList += gate.getGateToken().getConteudo();
					}
					
					wrongGateList += " ]";
					
					}
					
					SemanticErrorException se = new SemanticErrorException("There are more than one processes with the same asign. ' "+behaviourExpression.getProcessNameToken().getConteudo()+" "+wrongGateList+" "+" "+wrongSortList+" '", behaviourExpression.getIndex());
					processAssignError.add(se);
				}else{
					behaviourExpression.setProcessDefinition(processDefinitions.get(0));
				}
				
		}
		
		
	}

	private List<ProcessDefinition> checkProcessParameters(
			List<ProcessDefinition> processDefinitions,
			List<OpTable> parsingOpTables) throws SemanticErrorException{
		
		
		List<ProcessDefinition> processDefinitions2 = checkNumberOfParams(processDefinitions,
				parsingOpTables);
		
		
		
		//checar se os tipos de dados batem
		if(processDefinitions2.size() > 0){
			
			if(parsingOpTables != null && parsingOpTables.size() != 0){
			
			for (int j = 0; j < processDefinitions2.size(); j++) {
				
				for (int i = 0; i < processDefinitions2.get(j).getParameterList().size(); i++) {
				
					if(!processDefinitions2.get(j).getParameterList().get(i).getSort().getSortName().equalsIgnoreCase(parsingOpTables.get(i).getOpReturnSort())){
						
						processDefinitions2.remove(j);
						j--;
						break;
					}
					
				}
				
			}
			
			}
			
			if(processDefinitions2.size() == 0){
				String wrongSortList = "";
				
				for (OpTable opTable : parsingOpTables) {
					
					if(!wrongSortList.equals("")){
						wrongSortList += " ,";
					}
					
					wrongSortList += opTable.getOpReturnSort();
					
				}
				
				int index;
				
				
				index = parsingOpTables.get(0).getInicialIndex();
				
				
				throw new SemanticErrorException("There are no process definitions with the parameter signature ( "+wrongSortList+" ).", index);
			}
			
		}else{
			
			String wrongSortList = "";
			
			for (OpTable opTable : parsingOpTables) {
				
				if(!wrongSortList.equals("")){
					wrongSortList += " ,";
				}
				
				wrongSortList += opTable.getOpReturnSort();
				
			}
			
			int index;
			
			index = parsingOpTables.get(0).getInicialIndex();
			
			throw new SemanticErrorException("There are no process definitions with the same number of parameters ( "+wrongSortList+" ).", index);
		}
		
		
		return processDefinitions2;
		
		
	}

	private List<ProcessDefinition> checkNumberOfParams(
			List<ProcessDefinition> processDefinitions,
			List<OpTable> parsingOpTables) {
		
		List<ProcessDefinition> withSameNumberOfParams = new ArrayList<ProcessDefinition>();
		
		for (ProcessDefinition processDefinition : processDefinitions) {
			
			
			if((processDefinition.getParameterList()==null && parsingOpTables == null) || (processDefinition.getParameterList().size() == parsingOpTables.size())){
				withSameNumberOfParams.add(processDefinition);
			}
			
		}
		
		return withSameNumberOfParams;
	}

	private List<ProcessDefinition> chackProcessDefinitionExistence(Token token, int tokenIndex, Scope scope) throws SemanticErrorException {
		
		List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
		
		if(scope.getProcessDefinitions()!= null){
			
			for (ProcessDefinition processDefinition : scope.getProcessDefinitions()) {
				
				
				//pode mudar para só "equals"
				if(processDefinition.getTitule().getConteudo().equalsIgnoreCase(token.getConteudo())){
					processDefinitions.add(processDefinition);
				}
				
			}
			
		}
		
		while (scope != null){ 
		
			if((scope instanceof ProcessDefinition) && scope.getTitule().getConteudo().equalsIgnoreCase(token.getConteudo())){
				processDefinitions.add((ProcessDefinition)scope);
				break;
			}
			
			if(scope instanceof Specification && ((Specification) scope).getFlagInnerOrGlobalTypeDefinition()){
					((Specification) scope).setFlagInnerOrGlobalTypeDefinition(false);
			}else{
				scope = scope.getDadScope();
			}
		
		}
		
		if(processDefinitions.size() == 0){
			
			throw new SemanticErrorException("The process -> "+token.getConteudo()+" <- was not found on its context.", tokenIndex);

		}
		
		return processDefinitions;
	}

	private void checkPartialSync(BehaviourExpression behaviourExpression) {
		
		if(behaviourExpression.getSyncEventGates() != null){
			
			
			for (int i = 0; i < behaviourExpression.getSyncEventGates().size(); i++) {	
				
				checkGateExistence(behaviourExpression.getSyncEventGates().get(i),behaviourExpression.getScope().getVisibleGates(),behaviourExpression.getHiddenGates());
				
			}
			

			ExternalEnvironment.cleanExternalEnvironment();
			checkPartialSyncNegotiation(behaviourExpression);
			
		
		}
		
		
		
	}

	private void checkPartialSyncNegotiation(
			BehaviourExpression behaviourExpression) {
		
		for (Gate gate: behaviourExpression.getSyncEventGates()) {
			
			ExternalEnvironment.setSyncPoint(gate);
			
			boolean leftFlag = verifySyncGates(gate, behaviourExpression.getLeftBehaviourExpression());
			boolean rightFlag = verifySyncGates(gate, behaviourExpression.getRightBehaviourExpression());
			
			if(!(leftFlag && rightFlag) && rightFlag != leftFlag){
				PossibleDeadLock possibleDeadLock = new PossibleDeadLock("The event gate '"+gate.getGateToken().getConteudo()+"' was found just in one side of the sync.", gate.getTokenId());
				syncOfferPossibleDeadlock.add(possibleDeadLock);
				
			}
			
		}
		
		if(ExternalEnvironment.getSyncEventGates() != null){
			validateSyncOfferList(ExternalEnvironment.getSyncEventGates());
		}
	}

	private void validateSyncOfferList(ArrayList<Gate> syncEventGates) {
		
		String wrongString = "";
		
		int wrongIndex = -1;
		
		for (Gate eventGate : syncEventGates) {
			
			if(!wrongString.equals("")){
				wrongString += ", ";
				wrongIndex = eventGate.getTokenId();
			}
			
			wrongString += SystemHandler.mainView.getLineCount(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText().substring(0, eventGate.getTokenId()))+"";
			
			
		}		
		
		
		int numOfOffers = -1;
		
		boolean flag = true;
		
		for (Gate eventGate : syncEventGates) {
			
			
			int eventGateSize = !(eventGate instanceof EventGate) || (((EventGate)eventGate).getOffers() == null) ? 0 : ((EventGate)eventGate).getOffers().size();
			
			if(numOfOffers == -1){
				numOfOffers = eventGateSize;
			}else{
				if(eventGateSize != numOfOffers){
					
					PossibleDeadLock possibleDeadLock = new PossibleDeadLock("The event gate '"+eventGate.getGateToken().getConteudo()+"' have different offer number in its sync context.", eventGate.getTokenId());
					syncOfferPossibleDeadlock.add(possibleDeadLock);
					
					flag = false;
					break;
					
				}
				
			}
			
		}
		
		
		
		if(!flag){
					
			PossibleDeadLock possibleDeadLock = new PossibleDeadLock("Possible dead lock in lines '"+wrongString+"'. Problem with number of offers in the sync gates.", wrongIndex);
			syncOfferPossibleDeadlock.add(possibleDeadLock);
			
		}else{
		
		if(numOfOffers > 0){
			
		int cont = 0;
		
		flag = true;
		
		while(flag && syncEventGates.size()>0){
			
			String offerString = "";
			
			for (int i = 0; i < syncEventGates.size(); i++) {
				
				if(((EventGate)syncEventGates.get(i)).getOffers().size() <= cont){
					
					syncEventGates.remove(i);
					i--;
					
				}else{
				
				if(offerString.equals("")){
										
					offerString = ((EventGate)syncEventGates.get(i)).getOffers().get(cont).getOfferSort();
		
				}else{
					
					if(!offerString.equals(((EventGate)syncEventGates.get(i)).getOffers().get(cont).getOfferSort())){
						
						PossibleDeadLock possibleDeadLock = new PossibleDeadLock("The event gate '"+syncEventGates.get(i).getGateToken().getConteudo()+"' has offers in synchrony with offers of different sorts.", syncEventGates.get(i).getTokenId());
						syncOfferPossibleDeadlock.add(possibleDeadLock);
						
						flag = false;
						break;
						
					}
					
				}
				
				}
				
			}
							
			cont++;
			
		}
		
		if(!flag){
			
			PossibleDeadLock possibleDeadLock = new PossibleDeadLock("Possible dead lock in lines '"+wrongString+"'. Problem with sort of offers.", wrongIndex);
			syncOfferPossibleDeadlock.add(possibleDeadLock);
			
		}
		
		}
		}
		
	}

	private boolean verifySyncGates(Gate gate,
			BehaviourExpression behaviourExpression) {
		
		boolean myFlag = false;
		boolean leftFlag = false;
		boolean rightFlag = false;
		if(behaviourExpression != null){
		
			if(behaviourExpression.getOperator() ==  BehaviourExpression.ACTION_PREFIX){
				
				if(behaviourExpression.getPrefixedAction().getActionGate().getGateToken().getConteudo().equals(gate.getGateToken().getConteudo())){
					ExternalEnvironment.addSyncEventGate(behaviourExpression.getPrefixedAction().getActionGate());
					myFlag = true;
				}
				
			}
			
			leftFlag = verifySyncGates(gate, behaviourExpression.getLeftBehaviourExpression());
			rightFlag = verifySyncGates(gate, behaviourExpression.getRightBehaviourExpression());
			
		}
		
		return myFlag || leftFlag || rightFlag;
	}

	private void checkParallel(BehaviourExpression behaviourExpression) {
		
	}

	private void checkFullSync(BehaviourExpression behaviourExpression) {
		
	}

	private void checkParallelComposition(
			BehaviourExpression behaviourExpression) {
		if(behaviourExpression.getParallelCompositionType() == BehaviourExpression.FULL_SYNC){
			checkFullSync(behaviourExpression);
		}else if(behaviourExpression.getParallelCompositionType() == BehaviourExpression.PARALLEL){
			checkParallel(behaviourExpression);
		}else if(behaviourExpression.getParallelCompositionType() == BehaviourExpression.PARTIAL_SYNC){
			checkPartialSync(behaviourExpression);
		}
	}

	private void checkHiding(BehaviourExpression behaviourExpression) {
		
	}

	private void checkGuard(BehaviourExpression behaviourExpression) {
		
	}

	private void checkExit(BehaviourExpression behaviourExpression) {
		
	}

	private void checkEnabling(BehaviourExpression behaviourExpression) {
		
	}

	private void checkDisabling(BehaviourExpression behaviourExpression) {
		
	}

	private void checkChoice(BehaviourExpression behaviourExpression) {
	
	}

	private void checkActionPrefix(BehaviourExpression behaviourExpression) {
		if(!behaviourExpression.getPrefixedAction().getActionGate().getGateVisibility().equals(Gate.HIDEN_GATE))
			checkGateExistence(behaviourExpression.getPrefixedAction().getActionGate(),behaviourExpression.getScope().getVisibleGates(),behaviourExpression.getHiddenGates());
		
	}

	private Gate checkGateExistence(Gate actionGate, List<Gate> visibleGates,
			List<Gate> hiddenGates){
		
		boolean flag = false;
				
		if(hiddenGates != null){
			for (int i = 0; i < hiddenGates.size(); i++) {
				
				if(hiddenGates.get(i).getGateToken().getConteudo().equals(actionGate.getGateToken().getConteudo())){
					actionGate.setGateBridge(hiddenGates.get(i));
					return actionGate;
				}
			}
		}
		
		if(!flag){
			if(visibleGates != null){
				for (int i = 0; i < visibleGates.size(); i++) {
					if(visibleGates.get(i).getGateToken().getConteudo().equals(actionGate.getGateToken().getConteudo())){
						actionGate.setGateBridge(visibleGates.get(i));
						return actionGate;
					}
				}
			}
		}
		
			SemanticErrorException semanticErrorException = 
				new SemanticErrorException("The gate '"+actionGate.getGateToken().getConteudo()+"' not exists on the context.", actionGate.getTokenId());
			gateNotExistsErrors.add(semanticErrorException);
		
		return null;
		
	}

	private void verifyGateDuplicity(Scope scope) {
		
		for(Gate g : scope.getVisibleGates()){
			
			try{
				duplicatedGateErrorVerify(g, scope);
			}catch (SemanticErrorException sEx) {
				gateDublicateErrors.add(sEx);
			}
			
		}
		
		if(scope.getProcessDefinitions() != null){
		for(Scope scp : scope.getProcessDefinitions()){
			
			verifyGateDuplicity(scp);
			
		}}
		
	}

	
	
	public void verifyProcesses(Scope scope){
		
		
		if(scope.getProcessDefinitions() != null){
		
		for(Scope scp : scope.getProcessDefinitions()){
			try{
				
				
				duplicatedNameErrorVerify(scp, scope);
				
			}catch (SemanticErrorException sEx) {
				
				processDublicateErrors.add(sEx);
				
				
			}
			
			verifyProcesses(scp);
			
			
		}
		
		}
			
		
	}
	
	private void duplicatedNameErrorVerify(Scope scope, Scope externScope) throws SemanticErrorException{
		
		analyzingScope = scope;
		
		if(externScope!=null){
				
		int brotherSameName = brotherSameName(scope, externScope);
		int dadSameName = dadSameName(scope, externScope);
		
		if(brotherSameName!=-1){
			if(dadSameName!=-1){
				throw new SemanticErrorException("Other processes found with name -> "+scope.getTitule().getConteudo()+" <- at lines "+SystemHandler.getLineByToken(codTokens.get(brotherSameName))+" and "+SystemHandler.getLineByToken(codTokens.get(dadSameName)), analyzingScope.getTokenId());
			}
			throw new SemanticErrorException("Other process with name -> "+scope.getTitule().getConteudo()+" <- wad found at line "+SystemHandler.getLineByToken(codTokens.get(brotherSameName)), analyzingScope.getTokenId());
		}else if(dadSameName!=-1){                        
			throw new SemanticErrorException("Other process with name -> "+scope.getTitule().getConteudo()+" <- wad found at line "+SystemHandler.getLineByToken(codTokens.get(dadSameName)), analyzingScope.getTokenId());
		}
		
		}
			
	}
	
	public int brotherSameName(Scope scope, Scope externScope){
		

		
			for (Scope scp : externScope.getProcessDefinitions()){
				if(scp.getTitule().getConteudo().equals(scope.getTitule().getConteudo()) && scp!=scope){
					return scp.getTokenId();
				}
			}
		
		return -1;
	}
	
	
	public int dadSameName(Scope scope, Scope externScope) throws SemanticErrorException{
		
			
			if(scope.getTitule().equals(externScope.getTitule())){
				return externScope.getTokenId();
			}else{
				duplicatedNameErrorVerify(scope, externScope.getDadScope());
			}
		
		return -1;
	}

	private void gateNotExistsErrorVerify(Gate gate, Scope scope) throws SemanticErrorException {
		boolean exist = false;
		
		for(Gate g : scope.getVisibleGates()){
			
			if(g.getGateToken().getConteudo().equals(gate.getGateToken().getConteudo()) && gate!=g){
				exist = true;
				break;
			}
			
		}
		
		if(!exist)throw new SemanticErrorException("The gate -"+gate.getGateToken().getConteudo()+"- was not declared before for the process "+scope.getTitule()+" at line "+SystemHandler.getLineByToken(codTokens.get(gate.getTokenId())), gate.getTokenId());	
		
				
	}

	private void exitRulesBrokenErrorVerify() throws SemanticErrorException{
		
		if(analyzingScope.getFunctionality().getFunc()==Functionality.NO_EXIT){
			throw new SemanticErrorException("This process should never call exit, it was defined previously!!", indexToken);
		}
		
	}
		
	private void duplicatedGateErrorVerify(Gate gate, Scope scope) throws SemanticErrorException{
		
		if(scope.getVisibleGates().size()!=0){
			for(Gate g : scope.getVisibleGates()){
				if(g.getGateToken().getConteudo().equals(gate.getGateToken().getConteudo()) && gate!=g){
					throw new SemanticErrorException("The gate -> "+gate.getGateToken().getConteudo()+" <- has been declared previously at line "+SystemHandler.getLineByToken(codTokens.get(g.getTokenId())), gate.getTokenId());
				}
			}
		}
		
	}
	
}
