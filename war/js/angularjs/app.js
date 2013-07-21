var app = angular.module('CVsIntellect', [ 'ui.bootstrap', 'placeholderShim', 'CVsIntellectServices' ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "/user/partial/profile",
		controller : UserController,
		resolve : {
			userProfileData : function($q, $route, UserService) {
				var deferred = $q.defer();

				var successCallback = function(result) {
					if (angular.equals(result, [])) {
						deferred.reject("Could not get user data!");
					} else {
						deferred.resolve(result);
					}
				};

				UserService.getUserProfileData(successCallback);

				return deferred.promise;
			}
		}
	});
});

app.directive('contenteditable', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, ctrl) {
			var editor = CKEDITOR.inline(element[0]);

			editor.on('instanceReady', function() {
				scope.$apply(function() {
					ctrl.$setViewValue(editor.getData());
					scope.$broadcast('resetContentEditableModel');

					// remove title
					$(editor.element).attr('title', '');
					$(editor.element).attr('aria-label', '');
				});
			});

			// view -> model
			editor.on('pasteState', function() {
				scope.$apply(function() {
					ctrl.$setViewValue(editor.getData());
				});
			});

			// model -> view
			ctrl.$render = function() {
				editor.setData(ctrl.$viewValue);
			};

			// load init value from DOM
			ctrl.$render();
		}
	};
});

Array.prototype.move = function(old_index, new_index) {
	if ((0 <= old_index && old_index < this.length) && (0 <= new_index && new_index < this.length)) {
		this.splice(new_index, 0, this.splice(old_index, 1)[0]);
	}
	return this;
};

String.prototype.startsWith = function(str) {
	return this.indexOf(str) == 0;
};