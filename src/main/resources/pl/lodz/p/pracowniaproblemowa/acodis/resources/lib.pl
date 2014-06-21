:- initialization(initializeLib).

initializeLib :-
  infoMessageIsVisible('Loading Prolog libraries...'),
  load_library('alice.tuprolog.lib.BasicLibrary'),
  infoMessageIsVisible('BasicLibrary loaded.'),
  load_library('alice.tuprolog.lib.ISOLibrary'),
  infoMessageIsVisible('ISOLibrary loaded.'),
  load_library('alice.tuprolog.lib.IOLibrary'),
  infoMessageIsVisible('IOLibrary loaded.'),
  load_library('alice.tuprolog.lib.JavaLibrary'),
  infoMessageIsVisible('JavaLibrary loaded.').

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

isProfileDataBean(Bean) :-
  isBeanNamed(Bean, '#{profileDataBean}', 'pl.lodz.p.pracowniaproblemowa.acodis.profile.ProfileDataBean').

hasFilledProfile(Username) :-
  isProfileDataBean(Bean),
  Bean <- isFilledUser(Username) returns BooleanValue,
  BooleanValue = true.

hasBirthday(Username) :-
  isProfileDataBean(Bean),
  Bean <- isHappyBirthdayUser(Username) returns BooleanValue,
  BooleanValue = true.

urlConvertsToString(URL, String) :-
  class('java.net.URLDecoder') <- decode(URL, "UTF-8") returns String.

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

isSecond(Second) :-
  dateAndTime <- getSecond returns Second.

isMinute(Minute) :-
  dateAndTime <- getMinute returns Minute.

isHour(Hour) :-
  dateAndTime <- getHour returns Hour.

isDayOfWeek(DayOfWeek) :-
  dateAndTime <- getDayOfWeek returns DayOfWeek.

isDayOfMonth(DayOfMonth) :-
  dateAndTime <- getDayOfMonth returns DayOfMonth.

isMonth(Month) :-
  dateAndTime <- getMonth returns Month.

isYear(Year) :-
  dateAndTime <- getYear returns Year.

canAccess(AccessLevel) :- 
  isUser(User),
  isResource(Resource),
  canAccessAt(User, Resource, AccessLevel).

cannotAccess(AccessLevel) :-
  isUser(User),
  isResource(Resource),
  cannotAccessAt(User, Resource, AccessLevel).

canAccessAt(user(Username, Password), resource('login','login','login','login'), readAccess) :-
  isUsernameAndPassword(Username, Password).

canAccessAt(user(Username, Password), Resource, AccessLevel) :-
  Resource \= resource('login','login','login','login'),
  isUsernameAndPassword(Username, Password),
  userCanAccessAtIfLogged(Username, Resource, AccessLevel).

canAccessAt(user(Username, Password), Resource, AccessLevel) :-
  Resource \= resource('login','login','login','login'),
  isUsernameAndPassword(Username, Password),
  hasRole(Username, Role),
  roleCanAccessAtIfLogged(Role, Resource, AccessLevel).

cannotAccessAt(user(Username, Password), _, _) :-
  \+ isUsernameAndPassword(Username, Password).

cannotAccessAt(user(Username, Password), Resource, AccessLevel) :-
  Resource \= resource('login','login','login','login'),
  userCannotAccessAtIfLogged(Username, Resource, AccessLevel).

cannotAccessAt(user(Username, Password), Resource, AccessLevel) :-
  Resource \= resource('login','login','login','login'),
  hasRole(Username, Role),
  roleCannotAccessAtIfLogged(Role, Resource, AccessLevel).
