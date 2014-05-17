:- initialization(initialize).

initialize :-
  info('Loading Prolog libraries...'),
  load_library('alice.tuprolog.lib.BasicLibrary'),
  info('BasicLibrary loaded.'),
  load_library('alice.tuprolog.lib.ISOLibrary'),
  info('ISOLibrary loaded.'),
  load_library('alice.tuprolog.lib.IOLibrary'),
  info('IOLibrary loaded.'),
  load_library('alice.tuprolog.lib.JavaLibrary'),
  info('JavaLibrary loaded.'),
  info('Loading access rules...').

info(Text) :-
  logger <- info(Text).

warning(Text) :-
  logger <- warning(Text).

severe(Text) :-
  logger <- severe(Text).

concatenate([], '').

concatenate([Head|Tail], Result) :-
  concatenate(Tail, Rest),
  text_concat(Head, Rest, Result).
  

bean(Name, ClassName, Bean) :-
  passedContext <- getFacesContext returns FacesContext,
  FacesContext <- getApplication returns Application,
  class('java.lang.Class') <- forName(ClassName) returns Class,
  Application <- evaluateExpressionGet(FacesContext, Name, Class) returns Bean,
  !.

bean(Name, ClassName, Bean) :-
  concatenate(['Nie można załadować beana o nazwie: ', Name, '(', ClassName, ')'],Message),
  severe(Message),
  halt.

notNull(String,'') :-
  var(String),
  !.

notNull(String, String) :-
  nonvar(String),
  !.

loginBean(Bean) :-
  bean('#{login}', 'pl.lodz.p.pracowniaproblemowa.acodis.login.Login', Bean).

canAccess(AccessLevel) :- 
  passedContext <- getComponent returns ComponentOrNull,
  notNull(ComponentOrNull, Component),
  passedContext <- getCategory returns CategoryOrNull,
  notNull(CategoryOrNull, Category),
  passedContext <- getResource returns ResourceOrNull,
  notNull(ResourceOrNull, Resource),
  loginBean(LoginBean),
  LoginBean <- getUsername returns UsernameOrNull,
  notNull(UsernameOrNull, Username),
  LoginBean <- getRealPassword returns PasswordOrNull,
  notNull(PasswordOrNull, Password),
  concatenate([
    'Sprawdzam uprawnienia dla użytkownika: ',
    Username,
    ' o haśle: ',
    Password,
    ' dla komponentu: ',
    Component,
    ' dla kategorii: ',
    Category,
    ' dla zasobu: ',
    Resource,
    ' na poziomie: ',
    AccessLevel,
    '.'
  ],  Message),
  info(Message),
  canAccess(user(Username, Password,_), resource(Component, Category, Resource,_), AccessLevel),
  info('Uprawnienia przyznane.').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

password('test','test').

canAccess(user(Username, Password, Rest), Resource, AccessLevel) :-
  password(Username, Password),
  canAccessLogged(user(Username, Password, Rest), Resource, AccessLevel).

canAccessLogged(user(Username, Password, _), resource('login','login','login',_), readAccess).
