% Poniższe predykaty to predykaty systemowe. Nie należy ich zmieniać, jeśli nie wie sie co robi.

:- initialization(initialize).

initialize :-
  infoMessageIsVisible('Loading Prolog libraries...'),
  load_library('alice.tuprolog.lib.BasicLibrary'),
  infoMessageIsVisible('BasicLibrary loaded.'),
  load_library('alice.tuprolog.lib.ISOLibrary'),
  infoMessageIsVisible('ISOLibrary loaded.'),
  load_library('alice.tuprolog.lib.IOLibrary'),
  infoMessageIsVisible('IOLibrary loaded.'),
  load_library('alice.tuprolog.lib.JavaLibrary'),
  infoMessageIsVisible('JavaLibrary loaded.'),
  infoMessageIsVisible('Loading access rules...').

infoMessageIsVisible(Text) :-
  logger <- info(Text).

warningMessageIsVisible(Text) :-
  logger <- warning(Text).

severeMessageIsVisible(Text) :-
  logger <- severe(Text).

isConcatenationOf('', []).

isConcatenationOf(Concatenation,[Head|Tail]) :-
  isConcatenationOf(TailConcatenation, Tail),
  text_concat(Head, TailConcatenation, Concatenation).

isBeanNamed(Bean, Name, ClassName) :-
  passedContext <- getFacesContext returns FacesContext,
  FacesContext <- getApplication returns Application,
  class('java.lang.Class') <- forName(ClassName) returns Class,
  Application <- evaluateExpressionGet(FacesContext, Name, Class) returns Bean,
  !.

isBeanNamed(Bean, Name, ClassName) :-
  isConcatenationOf(Message, ['Nie można załadować beana o nazwie: ', Name, '(', ClassName, ')']),
  severeMessageIsVisible(Message),
  halt.

convertsToString(Variable,'') :-
  var(Variable),
  !.

convertsToString(String, String) :-
  nonvar(String),
  !.

isLoginBean(Bean) :-
  isBeanNamed(Bean, '#{login}', 'pl.lodz.p.pracowniaproblemowa.acodis.login.Login').

isUser(user(Username, Password)) :-
  isLoginBean(LoginBean),
  LoginBean <- getUsername returns UsernameOrVariable,
  convertsToString(UsernameOrVariable, Username),
  LoginBean <- getRealPassword returns PasswordOrVariable,
  convertsToString(PasswordOrVariable, Password).

isResource(resource(ComponentType, ComponentId, ResourceType, ResourceId)) :-
  passedContext <- getComponentType returns ComponentTypeOrVariable,
  convertsToString(ComponentTypeOrVariable, ComponentType),
  passedContext <- getComponentId returns ComponentIdOrVariable,
  convertsToString(ComponentIdOrVariable, ComponentId),
  passedContext <- getResourceType returns ResourceTypeOrVariable,
  convertsToString(ResourceTypeOrVariable, ResourceType),
  passedContext <- getResourceId returns ResourceIdOrVariable,
  convertsToString(ResourceIdOrVariable,ResourceId).

canAccess(AccessLevel) :- 
  isUser(User),
  isResource(Resource),
  canAccessAt(User, Resource, AccessLevel).

cannotAccess(AccessLevel) :-
  isUser(User),
  isResource(Resource),
  cannotAccessAt(User, Resource, AccessLevel).

% Poniższe predykaty to baza uprawnień. Można ją modyfikować aby nadać uprawnienia.

isPasswordFor('test','test').

canAccessAt(user(Username, Password), Resource, AccessLevel) :-
  isPasswordFor(Password, Username),
  canAccessAtIfLogged(user(Username, Password), Resource, AccessLevel).

canAccessAtIfLogged(_, resource('login','login','login','login'), readAccess).

cannotAccessAt(user(Username, Password), _, _) :-
  \+ isPasswordFor(Password, Username).
