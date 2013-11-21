angular.module('CVsIntellectServices', [ 'ngResource' ]).factory('UserService', function($resource) {
	var UserService = $resource('/user/getuserdata', {}, {
		getUserProfile : {
			method : 'GET',
			cache : true
		}
	});

	UserService.prototype.getUserProfileData = function(successCallback, failCallback) {
		return UserService.getUserProfile({}, successCallback, failCallback);
	};

	return new UserService;
});
