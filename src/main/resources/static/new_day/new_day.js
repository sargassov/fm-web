angular.module('new_day', ['ngStorage']).controller('new_dayController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback() {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.createNewDay = function () {
        $http.post(contextPath + '/new_day')
            .then(function successCallback(response) {
                $scope.newDayResponse = response.data;
                if (!$scope.newDayResponse.condition) {
                    window.location.href = 'match/match.html';
                }
                $scope.getActualDate();
            }, function errorCallback() {
                alert('CANT GO INTO THE FUTURE UNTIL ALL THE THINGS ARE DONE!');
            });
    };


    $scope.getActualDate();
});