/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015.08.06 */
(function (W, A, $) {
    'use strict';
    if (!DEBUG_MODE) {
        W.console = (function () {
            var c = {};
            var funs = ["log", "debug", "info", "warn", "error"];
            for (var i in funs) {
                var fun = funs[i];
                c[fun] = function (e) {
                };
            }
            return c;
        })();
    }

    W.vm = A.define({
        $id: 'mywalletCtrl',
        tabs: ['累计收入', '已发工资'],
        currentTabsIndex: 0,
        currentItemIndex: 0,
        switchTabs: function (index) {
            vm.currentTabsIndex = index;
        },
        balance: '',
        totalPrice: '',
        paidPrice: '',
        income: {},
        wage: {},
        incomeList: [],
        wageList: [],
        incomeCurrentPage: 1,
        wageCurrentPage: 1,
        isIncomeListLastPage: false,
        isWageListLastPage: false,
        getDetailRecord: function (self, month, type) {
            if ($(self).parent().prev('li').length == 0) return;
            var isLoad = $(self).data('load');
            if (!isLoad) {
                $(self).siblings('.bd').find('.loading').show();
                Http.getWalletDetail(month, type, function (res) {
                    if (res.success) {
                        switch (type) {
                            case 0:
                                for (var i in vm.incomeList.$model) {
                                    if (vm.incomeList[i].month == month) {
                                        vm.incomeList[i].details = res.data;
                                    }
                                }
                                scrollerA.refresh();
                                break;
                            case 1:
                                for (var j in vm.wageList.$model) {
                                    if (vm.wageList[j].month == month) {
                                        vm.wageList[j].details = res.data;
                                    }
                                }
                                scrollerB.refresh();
                                break;
                        }
                        $(self).siblings('.bd').find('.loading').hide();
                        $(self).data('load', 'true');
                    }
                });
            }

        }
    });
    A.filters.centToYuan = function (str) {
        var y = str / 100;
        y = y.toFixed(2);
        return y;
    };
    A.scan(document.body);
    document.addEventListener('touchmove', function (e) {
        e.preventDefault();
    }, false);
    var scrollerA, scrollerB;
    /**
     * 异步请求接口
     */
    var Http = {
        getWalletList: function (pageNo, type, cb) {
            $.post(WEB_SERVER + 'api/wallet/newWalletV14',
                {
                    token: TOKEN,
                    pageNo: pageNo,
                    count: 10,
                    type: type
                },
                function (res) {
                    console.log((type == 0 ? 'income' : 'wage') + " data:", res);
                    if (res.success) {
                        vm.balance = res.data.balance;
                        vm.totalPrice = res.data.walletDetail.totalPrice;
                        vm.paidPrice = res.data.walletDetail.paidPrice;
                        if (typeof cb == 'function')cb(res);
                    }
                }, 'json'
            )
        },
        getIncomeList: function (pageNo, type, cb) {
            this.getWalletList(pageNo, 0, function (res) {
                vm.income = res.data.walletDetail.page;
                switch (type) {
                    case 'refresh':
                        vm.incomeList = res.data.walletDetail.page.data;
                        break;
                    case 'more':
                        vm.incomeList = vm.incomeList.$model.concat(res.data.walletDetail.page.data);
                        break;
                    default :
                        break;
                }
                var pageCount = Math.ceil(res.data.walletDetail.page.totalcount / 10);
                vm.incomeCurrentPage = res.data.walletDetail.page.pageno;
                if (vm.incomeCurrentPage == pageCount) {
                    vm.isIncomeListLastPage = true;
                } else {
                    vm.isIncomeListLastPage = false;
                }
                console.log('isIncomeListLastPage:', vm.isIncomeListLastPage, 'incomeCurrentPage:', vm.incomeCurrentPage, 'pageCount:', pageCount);
                if (typeof cb == 'function')
                    cb(res);
            })
        },
        getWageList: function (pageNo, type, cb) {
            this.getWalletList(pageNo, 1, function (res) {
                vm.wage = res.data.walletDetail.page;
                switch (type) {
                    case 'refresh':
                        vm.wageList = res.data.walletDetail.page.data;
                        break;
                    case 'more':
                        vm.wageList = vm.wageList.$model.concat(res.data.walletDetail.page.data);
                        break;
                    default :
                        break;
                }
                var pageCount = Math.ceil(res.data.walletDetail.page.totalcount / 10);
                vm.wageCurrentPage = res.data.walletDetail.page.pageno;
                if (vm.wageCurrentPage == pageCount) {
                    vm.isWageListLastPage = true;
                } else {
                    vm.isWageListLastPage = false;
                }
                console.log('isWageListLastPage:', vm.isWageListLastPage, 'wageCurrentPage:', vm.wageCurrentPage, 'pageCount:', pageCount);
                if (typeof cb == 'function')
                    cb(res);
            })
        },
        getWalletDetail: function (month, type, cb) {
            $.post(WEB_SERVER + 'api/wallet/walletDetail',
                {
                    token: TOKEN,
                    monthTime: month,
                    type: type
                },
                function (res) {
                    console.log((type == 0 ? 'income' : 'wage') + " detail:", res);
                    if (typeof cb == 'function')cb(res);
                }, 'json'
            )
        }
    };
    /**初始化页面是加载数据*/
    Http.getIncomeList(1, 'refresh');
    Http.getWageList(1, 'refresh');

    $(function () {
        FastClick.attach(document.body);
        scrollerA = new Scroller('#recordWrapperA', {
            pullToRefresh: true,
            onPullToRefresh: function (callback) {
                Http.getIncomeList(1, 'refresh', function () {
                    callback(null);
                    scrollerA.refresh();
                });
            },
            pullToLoadMore: true,
            onPullToLoadMore: function (callback) {
                if (!vm.isIncomeListLastPage) {
                    vm.incomeCurrentPage = vm.incomeCurrentPage + 1;
                    Http.getIncomeList(vm.incomeCurrentPage, 'more', function () {
                        callback(null)
                    });
                } else {
                    callback(null)
                }
            }
        });
        scrollerB = new Scroller('#recordWrapperB', {
            pullToRefresh: true,
            onPullToRefresh: function (callback) {
                Http.getWageList(1, 'refresh', function () {
                    callback(null);
                    scrollerB.refresh();
                });
            },
            pullToLoadMore: true,
            onPullToLoadMore: function (callback) {
                if (!vm.isWageListLastPage) {
                    vm.wageCurrentPage = vm.wageCurrentPage + 1;
                    Http.getWageList(vm.wageCurrentPage, 'more', function () {
                        callback(null)
                    });
                } else {
                    callback(null)
                }
            }
        });
        $(document.body).on('click', '.record-list .hd', function () {
            var $hd = $(this);
            var $foldAllBtn = $hd.closest('.record-wrapper').siblings('.btn-fold-all');
            var $item = $hd.parent();
            var $pullToLoadMore = $hd.closest('.record-wrapper').find('.pullToLoadMore');
            var wHeight = $(window).height();
            if ($item.hasClass('active')) {
                $item.removeClass('active');
                if ($pullToLoadMore.offset().top < wHeight) {
                    var id = $hd.closest('.record-wrapper').attr('id');
                    switch (id) {
                        case 'recordWrapperA':
                            setTimeout(function () {
                                scrollerA.scrollToBottom();
                            }, 30);
                            break;
                        case 'recordWrapperB':
                            setTimeout(function () {
                                scrollerB.scrollToBottom();
                            }, 30);
                            break;
                        default :
                            break;
                    }
                }
            } else {
                $item.addClass('active');
            }
            var activeItems = $hd.closest('.record-list').find('li.active');
            if (activeItems.length == 0) {
                $foldAllBtn.removeClass('show');
            } else {
                $foldAllBtn.addClass('show');
            }
            if ($hd.closest('.record-wrapper').attr('id') == 'recordWrapperA') {
                scrollerA.refresh();
            }
            if ($hd.closest('.record-wrapper').attr('id') == 'recordWrapperB') {
                scrollerB.refresh();
            }
        });
        $('.btn-fold-all').click(function () {
            var items = $(this).siblings('.record-wrapper').find('.record-list>li');
            items.removeClass('active');
            $(this).removeClass('show');
            scrollerA.resize();
            scrollerB.resize();
        });
        W.scrollerA = scrollerA;
        W.scrollerB = scrollerB;
    });
}(window, avalon, Zepto));