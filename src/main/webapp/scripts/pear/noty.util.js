/**
 * jQuery NOTY Utils defined
 *
 * Date: 2015-12-07T16:01Z+08
 */
var NotyUtil = {};

(function() {
	/*默认的配置参数*/
	var defaults = {
		timeout: 5000,
		layout: "topRight",
		template: '<button type="button" class="close noty_close" data-dismiss="alert"></button><div class="noty_message"><span class="noty_text"></span></div>',
		closeWith: ["click", "button"],
		modal: false
	};
	
	var type = {
		ALERT: "alert",
		SUCCESS: "success",
		ERROR: "error",
		WARNING: "warning",
		INFORMATION: "information",
		CONFIRM: "confirm"
	};
	
	var generate = function(type, text, layout, theme, animation) {
		return noty({
            text: text,
            type: type,
            dismissQueue: true,
            layout: layout,
            closeWith: ["click"],
            theme: theme,
            maxVisible: 10,
            animation: animation
        });
	};
	
	/**
	 * 通过方法来创建不同的配置
	 */
	var createConfig = function(config) {
		return $.extend(true, {}, defaults, config);
	};
	
	/**
	 * 在浏览器窗口输出提示信息
	 */
	var out = function(text, level) {
		var lv = level || "success";
		lv = lv.toLowerCase();
		return noty(createConfig({
			text: text,
			type: level
		}));
	};

	/**
	 * 在浏览器窗口输出弹框提示信息
	 */
	var alert = function(text) {
		return noty(createConfig({
			text: text,
			type: "alert"
		}));
	};
	
	/**
	 * 在浏览器窗口输出日志提示信息
	 */
	var info = function(text) {
		return noty(createConfig({
			text: text,
			type: "information"
		}));
	};

	/**
	 * 在浏览器窗口输出成功提示信息
	 */
	var success = function(text) {
		return noty(createConfig({
			text: text,
			type: "success"
		}));
	};

	/**
	 * 在浏览器窗口输出警告提示信息
	 */
	var warn = function(text) {
		return noty(createConfig({
			text: text,
			type: "warning"
		}));
	};

	/**
	 * 在浏览器窗口输出错误提示信息
	 */
	var error = function(text) {
		return noty(createConfig({
			text: text,
			type: "error"
		}));
	}
	
	NotyUtil.defaults = defaults;
	NotyUtil.type = type;
	NotyUtil.generate = generate;
	NotyUtil.createConfig = createConfig;
	NotyUtil.out = out;
	NotyUtil.info = info;
	NotyUtil.success = success;
	NotyUtil.warn = warn;
	NotyUtil.error = error;
})();
