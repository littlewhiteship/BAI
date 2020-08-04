import scrapy
from weiboSpider.items import WeibospiderItem

class WeiboSpider(scrapy.Spider):
    name = 'weibo'
    allowed_domains = ['weibo.com']
    # start_urls = ['http://weibo.com/']

    def start_requests(self):
       # 女装博主
        yield scrapy.Request('https://s.weibo.com/user?q=%E5%A5%B3%E8%A3%85%E5%8D%9A%E4%B8%BB&Refer=weibo_user', self.parse)

    def parse(self, response):
        item = WeibospiderItem()
        selectors = response.xpath('//div[@class="card-wrap"]')
        for selector in selectors:
            if selector.xpath('./div/div[@class="info"]/div/a[1]').get() is not None:
                item['name'] = selector.xpath('./div/div[@class="info"]/div/a[1]').get()
            if selector.xpath('./div/div/a/img/@src').get() is not None:
                item['img'] = selector.xpath('./div/div/a/img/@src').get()
            if selector.xpath('./div/div[@class="info"]/p/span[2]/a/text()').get() is not None:
                item['fans'] = selector.xpath('./div/div[@class="info"]/p/span[2]/a/text()').get()
            if selector.xpath('./div/div/a/@href').get() is not None:
                link = selector.xpath('./div/div/a/@href').get()
                link = str(link)
                item['link'] = "https:"+link
            if selector.xpath('./div/div[@class="info"]/p/span[3]/a/text()').get() is not None:
                item['blog'] = selector.xpath('./div/div[@class="info"]/p/span[3]/a/text()').get()
            item['blogtype'] = "女装博主"
            print(item['name'])
            print(item['img'])
            print(item['fans'])
            print(item['link'])
            print(item['blog'])
            print(item['blogtype'])

