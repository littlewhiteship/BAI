import scrapy
from manmanSpider.items import ManmanspiderItem
import re

class ManmanSpider(scrapy.Spider):
    name = 'manman'
    allowed_domains = ['manmanbuy.com']
    # start_urls = ['http://manmanbuy.com/']

    def start_requests(self):
        #手机
        #yield scrapy.Request('http://www.manmanbuy.com/cpl_57_qz_0_0_0_0_0_0_0_0_0_0_0_1.aspx', self.parse)
        # 数码相机
        #yield scrapy.Request('http://www.manmanbuy.com/cpl_58_qz_0_0_0_0_0_0_0_0_0_0_0_1.aspx', self.parse)
        # 智能手环
        # yield scrapy.Request('http://www.manmanbuy.com/cpl_1747_qz_0_0_0_0_0_0_0_0_0_0_0_1.aspx', self.parse)
        # 移动电源
        # yield scrapy.Request('http://www.manmanbuy.com/cpl_900_qz_0_0_0_0_0_0_0_0_0_0_0_1.aspx', self.parse)
        # 洁面乳
        yield scrapy.Request('http://www.manmanbuy.com/cpl_886_qz_0_0_0_0_0_0_0_0_0_0_0_1.aspx', self.parse)



    def parse(self, response):
        links = response.xpath('//div[@class="item"]/div[4]/a/@href').getall()
        for link in links:
            print(link)
            yield scrapy.Request("http://www.manmanbuy.com/"+str(link), self.parse_page)


    def parse_page(self, response):
        selectors = response.xpath('//ul/li')
        point = 99
        i = 0
        for selector in selectors:
            if selector.xpath('./div/div[@class="title"]/div[1]/a/text()').get() is not None:
                item = ManmanspiderItem()
                i += 1
                if selector.xpath('./div/div[@class="title"]/div[1]/a/text()').get() is not None:
                    item['name'] = selector.xpath('./div/div[@class="title"]/div[1]/a/text()').get()
                    print(item['name'])
                if selector.xpath('./div/div[@class="cost"]/div/span[@class="listpricespan"]/text()').get() is not None:
                    item['price'] = selector.xpath('./div/div[@class="cost"]/div/span[@class="listpricespan"]/text()').get()
                    print(item['price'])
                if selector.xpath('./div/div/a/img/@src').get() is not None:
                    item['image'] = selector.xpath('./div/div/a/img/@src').get()
                    print(item['image'])
                if selector.xpath('./div/div/a/@href').get() is not None:
                    link = selector.xpath('./div/div/a/@href').get()
                    link = str(link)
                    pattern = re.compile(r'^h')
                    if pattern.match(link) is not None:
                        item['link'] = link
                    else:
                        item['link'] = "http://www.manmanbuy.com/"+link
                    print(item['link'])
                if selector.xpath('./div/div[@class="mall"]/p[@class="m"]/a/text()').get() is not None:
                    item['platform'] = selector.xpath('./div/div[@class="mall"]/p[@class="m"]/a/text()').get()
                    print(item['platform'])
                if selector.xpath('./div/div[@class="comment"]/a/span/text()').get() is not None:
                    item['sales'] = selector.xpath('./div/div[@class="comment"]/a/span/text()').get()
                    print(item['sales'])

                item['point'] = point
                point *= 0.9
                print(item['point'])

                yield item

                if i == 5:
                    self.crawler.engine.close_spider(self, '停止！')

