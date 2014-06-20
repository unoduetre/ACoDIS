:- initialization(initializeAccess).

initializeAccess :-
  infoMessageIsVisible('Access rules loaded.').

isUsernameAndPassword('test','test').
isUsernameAndPassword('no','no').
isUsernameAndPassword('read','read').
isUsernameAndPassword('write','write').
isUsernameAndPassword('admin','admin').
isUsernameAndPassword('zenek','zenek').
isUsernameAndPassword('franek','franek').
isUsernameAndPassword('mateusz','mateusz').

hasRole('admin','administrators').
hasRole('mateusz','administrators').
hasRole('zenek','wikipedists').
hasRole('franek','fileadmins').

hasRole(Username,'wikipedists') :-
  hasRole(Username, 'administrators').
hasRole(Username,'fileadmins') :- 
  hasRole(Username, 'administrators').

userCanAccessAtIfLogged('read',_, readAccess).
userCanAccessAtIfLogged('write',_, writeAccess).

roleCanAccessAtIfLogged('administrators',_, writeAccess).
