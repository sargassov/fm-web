angular.module('training_balance', ['ngStorage']).controller('training_balanceController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadPlayersOnTrainSort = function (parameter) {
        $http.get(contextPath + '/team/players_on_training/' + parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback(response) {
                alert('PLAYERS ON TRAIN OF YOUR TEAM NOT FOUND');
            });
    };

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };

    // $scope.getInfoAboutPlayer = function (name) {
    //     $http.get(contextPath + '/player/' + name)
    //         .then(function successCallback(response) {
    //             $localStorage.rootPlayer = response.data;
    //             window.location.href = '../player_window/player_window.html';
    //         }, function errorCallback(response) {
    //             alert('PLAYERS WITH NAME ' + name + ' NOT FOUND');
    //         });
    // };


    $scope.getActualDate();
    $scope.loadPlayersOnTrainSort(0);
    $scope.getUserTeamName();
});