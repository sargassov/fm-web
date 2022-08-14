angular.module('new_day', ['ngStorage']).controller('new_dayController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.createNewDay = function () {
        $http.post(contextPath + '/new_day')
            .then(function successCallback(response) {
                $scope.getActualDate();
            }, function errorCallback(response) {
                alert('CANT GO INTO THE FUTURE UNTIL ALL THE THINGS ARE DONE!');
            });
    };


    $scope.getActualDate();
});