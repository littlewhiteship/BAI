# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
import pymysql


def dbHandle():
    conn = pymysql.connect(
        host="192.168.57.112",
        user="root",
        passwd="Root@123",
        charset="utf8",
        use_unicode=False
    )
    return conn

class ManmanspiderPipeline:
    def process_item(self, item, spider):
        dbObject = dbHandle()
        cursor = dbObject.cursor()
        cursor.execute("USE mer")
        # 插入数据库
        sql = "INSERT INTO t_allmcredit(amcname,price,link,monthlysales,platform,amcimage,creditscore) VALUES(%s,%s,%s,%s,%s,%s,%s)"
        try:
            cursor.execute(sql,
                           (item['name'], item['price'], item['link'], item['sales'], item['platform'], item['image'], item['point']))
            cursor.connection.commit()
        except BaseException as e:
            print("错误在这里>>>>>>>>>>>>>", e, "<<<<<<<<<<<<<错误在这里")
            dbObject.rollback()
        return item
