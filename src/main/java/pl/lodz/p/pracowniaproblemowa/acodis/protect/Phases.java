package pl.lodz.p.pracowniaproblemowa.acodis.protect;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseEvent;

public class Phases implements PhaseListener
{
  private Logger logger = Logger.getLogger(Phases.class.getName());

  public void beforePhase(PhaseEvent event)
  {
    PhaseId phaseId = event.getPhaseId();
    FacesContext facesContext = event.getFacesContext();
    logger.info("##### STARTING PHASE "+phaseId.getName()+" #####");
    if(phaseId.equals(PhaseId.RENDER_RESPONSE) && facesContext.getViewRoot() != null)
    {
      logger.info("Resetting view root");
      facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, facesContext.getViewRoot().getViewId()));
    }
  }

  public void afterPhase(PhaseEvent event)
  {
    PhaseId phaseId = event.getPhaseId();
    FacesContext facesContext = event.getFacesContext();
    logger.info("##### STOPPING PHASE "+phaseId.getName()+" #####");
  }

  public PhaseId getPhaseId()
  {
    return PhaseId.ANY_PHASE;
  }
}

