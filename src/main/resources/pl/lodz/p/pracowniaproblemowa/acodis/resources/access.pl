%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% użytkownicy do testów

% testowy użytkownik no
isUsernameAndPassword(no, no). 
% testowy użytkownik read
isUsernameAndPassword(read, read). 
% testowy użytkownik write
isUsernameAndPassword(write, write). 
% testowi użytkownicy
isUsernameAndPassword(test1, test1).
isUsernameAndPassword(test2, test2).
isUsernameAndPassword(test3, test3).

% prawo odczytu dla zalogowanego użytkownika read do dowolnego zasobu
userCanAccessAtIfLogged(read, _, readAccess). 
% prawo odczytu dla zalogowanego użytkownika write do dowolnego zasobu
userCanAccessAtIfLogged(write, _, writeAccess). 
% prawa dla pozostałych użytkowników testowych
% gdy pola wypełnione
userCanAccessAtIfLogged(test1, _, writeAccess) :-
    isFilled.
% gdy minuta nieparzysta
userCanAccessAtIfLogged(test2, _, writeAccess) :-
    isMinute(Minute),
    1 is Minute - 2 * (Minute // 2).

userCanAccessAtIfLogged(test3, resource(filebrowser, _, filesystementry, _), readAccess).
userCanAccessAtIfLogged(test3, resource(filebrowser, _, filesystementry, _), writeAccess) :-
    isFresh.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% właściwa baza uprawnień
% możliwe role: admin, wikiAdmin, wikipedist, fileAdmin, hrSpecialist, hrAdmin, ruleAdmin

isUsernameAndPassword(jadzia, jadzia).
hasRole(jadzia, admin).

isUsernameAndPassword(zosia, zosia).
hasRole(zosia, wikiAdmin).

isUsernameAndPassword(kasia, kasia).
hasRole(kasia, fileAdmin).

isUsernameAndPassword(wiesia, wiesia).
hasRole(wiesia, hrAdmin).

isUsernameAndPassword(basia, basia).
hasRole(basia, hrAdmin).
hasRole(basia, fileAdmin).

isUsernameAndPassword(janek, janek).
hasRole(janek, wikipedist).

isUsernameAndPassword(franek, franek).
hasRole(franek, hrSpecialist).

isUsernameAndPassword(zenek, zenek).
hasRole(zenek, ruleAdmin).

isUsernameAndPassword(pablo, pablo).

hasRole(Username, wikiAdmin) :- 
    hasRole(Username, fileAdmin),
    hasRole(Username, hrAdmin).

hasRole(Username, hrAdmin) :-
    hasRole(Username, ruleAdmin).

hasRole(Username, fileAdmin) :-
    hasRole(Username, ruleAdmin).

hasRole(Username, wikipedist) :-
    hasRole(Username, wikiAdmin).

hasRole(Username, hrSpecialist) :-
    hasRole(Username, hrAdmin).

roleCanAccessAtIfLogged(admin, _, writeAccess).

roleCanAccessAtIfLogged(fileAdmin, resource(filebrowser, _, filesystementry, _), writeAccess).

roleCanAccessAtIfLogged(wikipedist, resource(editorWrapper, editor, _, _), writeAccess).
roleCanAccessAtIfLogged(wikipedist, resource(reader, reader, _, _), writeAccess).

roleCanAccessAtIfLogged(wikiAdmin, resource(categoryManagerNew, categoryManagerNew, categoryManagerNew, categoryManagerNew), writeAccess).
roleCanAccessAtIfLogged(wikiAdmin, resource(categoryManager, categoryManager, categoryManager, categoryManager), writeAccess).

roleCanAccessAtIfLogged(ruleAdmin, resource(security, security, security, security), readAccess).
roleCanAccessAtIfLogged(ruleAdmin, resource(security, security, security, security), writeAccess) :-
    isHour(9).
roleCanAccessAtIfLogged(ruleAdmin, resource(security, security, security, security), writeAccess) :-
    isHour(17).
roleCanAccessAtIfLogged(ruleAdmin, resource(security, security, security, security), writeAccess) :-
    isHour(Hour),
    Hour >= 12,
    Hour =< 13.

roleCanAccessAtIfLogged(hrSpecialist, resource(profile, profile, basicProfile, _), readAccess).
roleCanAccessAtIfLogged(hrSpecialist, resource(profileReader, profileReader, basicProfile, _), readAccess).
roleCanAccessAtIfLogged(hrSpecialist, resource(profileMoreInfo, profileMoreInfo, extendedProfile, _), readAccess).
roleCanAccessAtIfLogged(hrSpecialist, resource(profileReminder, profileReminder, reminder, _), readAccess).
roleCanAccessAtIfLogged(hrSpecialist, resource(reader, reader, hr, _), readAccess).

roleCanAccessAtIfLogged(hrAdmin, resource(editorWrapper, editor, hr, _), writeAccess).
roleCanAccessAtIfLogged(hrAdmin, resource(reader, reader, hr, _), writeAccess).

userCanAccessAtIfLogged(Username, resource(profile, profile, basicProfile, Username), writeAccess).
userCanAccessAtIfLogged(Username, resource(profileReader, profileReader, basicProfile, Username), readAccess).
userCanAccessAtIfLogged(Username, resource(profileEditor, profileEditor, basicProfile, Username), writeAccess).
userCanAccessAtIfLogged(Username, resource(profileMoreInfo, profileMoreInfo, extendedProfile, Username), writeAccess).
userCanAccessAtIfLogged(Username, resource(profileReminder, profileReminder, reminder, Username), readAccess).

userCanAccessAtIfLogged(Username, resource(profile, profile, basicProfile, _), readAccess) :-
    hasFilledProfile(Username).
userCanAccessAtIfLogged(Username, resource(profileReader, profileReader, basicProfile, _), readAccess) :-
    hasFilledProfile(Username).
userCanAccessAtIfLogged(Username, resource(profileMoreInfo, profileMoreInfo, extendedProfile, _), readAccess) :-
    hasFilledProfile(Username).

userCanAccessAtIfLogged(Username, resource(filebrowser, _, filesystementry, _), readAccess) :-
    hasSomeParentDirNamePrefix(Username).
userCanAccessAtIfLogged(Username, resource(filebrowser, _, filesystementry, _), writeAccess) :-
    hasEntryNamePrefix(Username).

