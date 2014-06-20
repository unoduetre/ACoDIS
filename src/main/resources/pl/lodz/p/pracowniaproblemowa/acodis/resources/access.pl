%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% użytkownicy do testów

% testowy użytkownik no
isUsernameAndPassword(no,no). 
% testowy użytkownik read
isUsernameAndPassword(read,read). 
% testowy użytkownik write
isUsernameAndPassword(write,write). 
% testowi użytkownicy
isUsernameAndPassword(test1,test1).
isUsernameAndPassword(test2,test2).

% prawo odczytu dla zalogowanego użytkownika read do dowolnego zasobu
userCanAccessAtIfLogged(read,_,readAccess). 
% prawo odczytu dla zalogowanego użytkownika write do dowolnego zasobu
userCanAccessAtIfLogged(write,_,writeAccess). 
% prawa dla pozostałych użytkowników testowych
userCanAccessAtIfLogged(test1,_,writeAccess) :- isFilled.
userCanAccessAtIfLogged(test2,_,writeAccess) :-
    isMinute(Minute),
    1 is Minute - 2 * (Minute // 2).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% właściwa baza uprawnień
% możliwe role: admin, wikiAdmin, wikipedist, fileAdmin, hrSpecialist, hrAdmin, ruleAdmin

isUsernameAndPassword(jadzia,jadzia).
hasRole(jadzia, admin).

isUsernameAndPassword(zosia,zosia).
hasRole(zosia,wikiadmin).

isUsernameAndPassword(kasia,kasia).
hasRole(kasia,fileadmin).

isUsernameAndPassword(basia,basia).
hasRole(basia,hradmin).
hasRole(basia,fileadmin).

isUsernameAndPassword(janek,janek).
hasRole(janek,wikipedist).

isUsernameAndPassword(franek,franek).
hasRole(franek,hrSpecialist).

isUsernameAndPassword(zenek,zenek).
hasRole(zenek,ruleAdmin).

hasRole(Username, wikiAdmin) :- 
    hasRole(Username, fileAdmin),
    hasRole(Username, hrAdmin).

hasRole(Username, hrAdmin) :-
    hasRole(Username, ruleAdmin).

hasRole(Username, fileAdmin) :-
    hasRole(Username, ruleAdmin).

hasRole(Username, wikiedist) :-
    hasRole(Username, wikiAdmin).

hasRole(Username, hrSpecialist) :-
    hasRole(Username, hrAdmin).

roleCanAccessAtIfLogged(admin,_,writeAccess).
roleCanAccessAtIfLogged(fileAdmin,resource(filebrowser,filebrower,filesystementry,_),writeAccess).
roleCanAccessAtIfLogged(wikiAdmin,resource(categoryManagerNew, categoryManagerNew, categoryManagerNew, categoryManagerNew),writeAccess).
roleCanAccessAtIfLogged(wikiAdmin,resource(categoryManager, categoryManager, categoryManager, categoryManager),writeAccess).
roleCanAccessAtIfLogged(wikipedist,resource(editorWrapper, editorWrapper, _, _),readAccess).


