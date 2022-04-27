angular.module('registr', ['ngStorage']).controller('registrController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

//     if ($localStorage.springWebUser) {
//         $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
//     }
//
// // войти
//     $scope.tryToAuth = function () {
//         $http.post('http://localhost:8189/app/auth', $scope.user)
//             .then(function successCallback(response) {
//                 if (response.data.token) {
//                     $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
//                     $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
//                 }
//             }, function errorCallback(response) {
//                 alert('WRONG USERNAME OR PASSWORD');
//             });
//     };
// // выйти
//     $scope.tryToLogout = function () {
//         $scope.clearUser();
//         if ($scope.user.username) {
//             $scope.user.username = null;
//         }
//         if ($scope.user.password) {
//             $scope.user.password = null;
//         }
//     };
// // почистить данные пользователя
//     $scope.clearUser = function () {
//         delete $localStorage.springWebUser;
//         $http.defaults.headers.common.Authorization = '';
//     };
// // проверка на аутентификацию
//     $rootScope.isUserLoggedIn = function () {
//         if ($localStorage.springWebUser) {
//             return true;
//         } else {
//             return false;
//         }
//     };
    $scope.createNewGame = function () {
        $http.post(contextPath + '/new_game', $scope.user)
            .then(function successCallback(response) {
                window.location.href = '../main_menu/main_menu.html';
            }, function errorCallback(response) {
                alert('NEW GAME WAS NOT CREATED');
            });
    };


});