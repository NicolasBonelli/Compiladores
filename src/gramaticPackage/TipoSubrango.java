package gramaticPackage;

public class TipoSubrango {
    String tipoBase;
    double limiteInferior;
    double limiteSuperior;
  
    public TipoSubrango(String tipoBase, double limiteInferior, double limiteSuperior) {
        this.tipoBase = tipoBase;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }
  
    
    @Override
    public String toString(){
      return tipoBase + " - Limite inferior: "+ limiteInferior + " - Limite Superior: "+ limiteSuperior;
    }
  }
