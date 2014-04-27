package pl.lodz.p.pracowniaproblemowa.acodis.prolog;

import javax.faces.context.FacesContext;

import alice.tuprolog.Term;
import alice.tuprolog.Struct;

public class PassedContext
{
  FacesContext facesContext = null;
  String component = null;
  String category = null;
  String resource = null;

  public PassedContext(FacesContext facesContext, String component, String category, String resource)
  {
    this.facesContext = facesContext;
    this.component = component;
    this.category = category;
    this.resource = resource;
  }

  public Term toTerm()
  {
    return new Struct("passedContext", new Struct(component));
  }
}

