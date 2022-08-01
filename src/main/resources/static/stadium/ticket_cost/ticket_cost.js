angular.module('ticket_cost', ['ngStorage']).controller('ticket_costController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadCurrentStadiumStatus = function () {
        $http.get(contextPath + '/stadium/status_info')
            .then(function successCallback(response) {
                $scope.StadiumCurrentInfo = response.data;
            }, function errorCallback(response) {
                alert('STADIUM CURRENT INFO NOT FOUND');
            });
    };

    $scope.loadTicketCostInformation = function () {
        $http.get(contextPath + '/stadium/ticket_cost/information')
            .then(function successCallback(response) {
                $scope.TicketCostInformation = response.data;
            }, function errorCallback(response) {
                alert('TICKET COST INFORMATION NOT FOUND');
            });
    };

    $scope.changeTicketCost = function (type, delta) {
        var obj = {type: type, value: delta}
        $http.post(contextPath + '/stadium/ticket_cost/change', obj)
            .then(function successCallback(response) {
                $scope.loadCurrentStadiumStatus();
                $scope.loadTicketCostInformation();
            }, function errorCallback(response) {
                alert('CHANGE OF TICKET COSY NOT AVAILABLE');
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


    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.loadCurrentStadiumStatus();
    $scope.loadTicketCostInformation();
});