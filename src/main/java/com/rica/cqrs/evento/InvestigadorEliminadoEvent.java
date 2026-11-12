package com.rica.cqrs.evento;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvestigadorEliminadoEvent {
    @JsonProperty("investigador_correo")
    private String investigadorCorreo;
    
    public InvestigadorEliminadoEvent() {}
    
    public InvestigadorEliminadoEvent(String investigadorCorreo) {
        this.investigadorCorreo = investigadorCorreo;
    }
    
    public String getInvestigadorCorreo() { return investigadorCorreo; }
    public void setInvestigadorCorreo(String investigadorCorreo) { this.investigadorCorreo = investigadorCorreo; }
}
