/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	config.autoGrow_onStartup = true;
	config.forcePasteAsPlainText = true;
	config.enterMode = CKEDITOR.ENTER_BR;
	config.coreStyles_bold = { element : 'b', overrides : 'strong' };
	config.coreStyles_italic = { element : 'i', overrides : 'em' };
	config.removePlugins = 'liststyle';
	config.toolbar = [ [ 'Bold', 'Italic', 'Underline', '-', 'NumberedList', 'BulletedList', '-' ] ];
	//config.title = false;
};
