/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015.06.19 */
(function (W, A, $) {
    'use strict';

    W.vm = A.define({
        $id: 'appCtrl',
        initialized: false,
        qaVo: {}
    });
    A.scan();

    /**异步请求**/
    var Http = {
        /** 获取题目详细信息 */
        getQaDetailInfo: function (callback) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: WEB_SERVER + 'api/question/shareAudioDetail',
                data: {
                    teacherId: Params.teacherId,
                    questionId: Params.questionId
                },
                success: function (res) {
                    console.log(res);
                    callback(res)
                }
            });
        }
    };
    Http.getQaDetailInfo(function (res) {
        vm.qaVo = res.data;
        if (vm.qaVo.audioType != 2) {
            AudioPlayer('audioPlayer', vm.qaVo.audioUrl);
        }
        vm.initialized = true;
    });

    $(function () {
        FastClick.attach(document.body);
        $('#showMoreBtn').on('click', function () {
            var $btn = $(this);
            var $qaCnt = $('#qaContent');
            if ($btn.hasClass('unfold')) {
                $qaCnt.removeClass('more');
                $btn.removeClass('unfold').children('span').text('显示全部');
            } else {
                $qaCnt.addClass('more');
                $btn.addClass('unfold').children('span').text('收起');
            }

        });
        $('#playerShowBtn').on('click', function () {
            if (vm.qaVo.audioType == 2) {
                if (confirm('请下载学习宝客户端观看白板讲解')) {
                    window.location.href = 'http://www.xuexibao.cn/html/download.html';
                }
            } else {
                $(this).siblings('.play-ani').css('display', 'inline-block');
                $(this).hide();
                $('.audio-player-wrapper').removeClass('hide');
            }
        });


    });

    /**
     * @Description AudioPlayer
     * @param id
     */
    var AudioPlayer = function (id, url) {
        if (!(this instanceof AudioPlayer)) {
            return new AudioPlayer(id, url);
        }
        this.audioPlayer = $("#" + id);
        this.audioUrl = url;
        this.elem = {};
        this.isCanplay = false;
        this.init();
    };

    AudioPlayer.prototype = {
        constructor: AudioPlayer,
        init: function () {
            var that = this;
            that.createPlayer();
            that.elem = {
                audioTrack: that.audioPlayer.children('audio')[0],
                playhead: that.audioPlayer.find('.progress'),
                loaded: that.audioPlayer.find('.loaded'),
                playedTime: that.audioPlayer.find('.played'),
                duration: that.audioPlayer.find('.duration'),
                playbtn: that.audioPlayer.find('.play-pause')
            };
            that.bufferBar();
            that.bindEvent()
        },

        createPlayer: function () {
            var that = this;
            var markup = '<audio src="' + that.audioUrl + '" preload="none"></audio>'
                + '<div class="player-left">'
                + '<div class="scrubber">'
                + '<div class="progress"></div>'
                + '<div class="loaded"></div>'
                + '</div>'
                + '<div class="time"><em class="played">00:00</em>/<strong class="duration">00:00</strong></div>'
                + '<div class="error-message"></div>'
                + '</div>'
                + '<div class="play-pause">'
                + '<p class="play"></p>'
                + '<p class="pause"></p>'
                + '<p class="loading"></p>'
                + '<p class="error"></p>'
                + '</div>';
            that.audioPlayer.html(markup);
        },

        playPause: function () {
            var that = this;
            if (that.elem.audioTrack.paused) {
                that.elem.audioTrack.play();
                that.audioPlayer.addClass('playing');
                if (!that.isCanplay) {
                    that.audioPlayer.addClass('loading');
                }
            } else {
                that.elem.audioTrack.pause();
                that.audioPlayer.removeClass('playing');
            }
        },

        updatePlayhead: function () {
            var that = this;
            var progressPercent = ( that.elem.audioTrack.currentTime / that.elem.audioTrack.duration) * 100 + "%";
            that.elem.playhead.width(progressPercent);
            that.elem.playedTime.html(that.formatTime(that.elem.audioTrack.currentTime));
            if (!isNaN(that.elem.audioTrack.duration)) {
                that.elem.duration.html(that.formatTime(that.elem.audioTrack.duration))
            }
        },

        bufferBar: function () {
            var that = this;
            var bufferTimer = setInterval(function () {
                var bufferIndex = that.elem.audioTrack.buffered.length;
                if (bufferIndex > 0 && that.elem.audioTrack.buffered != undefined) {
                    var bufferValue = that.elem.audioTrack.buffered.end(bufferIndex - 1) / that.elem.audioTrack.duration;
                    that.elem.loaded.width(bufferValue * 100 + '%');
                    if (Math.abs(that.elem.audioTrack.duration - that.elem.audioTrack.buffered.end(bufferIndex - 1)) < 1) {
                        that.elem.loaded.width('100%');
                        clearInterval(bufferTimer);
                    }
                }
            }, 1000);
        },

        bindEvent: function () {
            var that = this;
            $(that.elem.audioTrack).on({
                'timeupdate': function () {
                    that.updatePlayhead()
                },
                'canplay': function () {
                    that.isCanplay = true;
                    that.audioPlayer.removeClass('loading');
                },
                'playing': function () {
                    $('.play-ani').removeClass('play-ani-pasue')
                },
                'pause': function () {
                    $('.play-ani').addClass('play-ani-pasue')
                },
                'ended': function () {
                    that.audioPlayer.removeClass('playing');
                }
            });

            that.elem.playbtn.on('click', function () {
                that.playPause()
            });

        },

        formatTime: function (second) {
            var s = parseInt(second % 60);
            var m = parseInt((second / 60) % 60);
            s = (s >= 10) ? s : "0" + s;
            m = (m >= 10) ? m : "0" + m;
            return m + ':' + s;
        }

    }
}(window, avalon, Zepto));