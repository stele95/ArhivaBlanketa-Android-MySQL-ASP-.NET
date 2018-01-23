using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WEB_API.Mapiranja;
using FluentNHibernate.Cfg;
using FluentNHibernate.Cfg.Db;
using NHibernate;

namespace WEB_API
{
    class DataLayer
    {
        private static ISessionFactory _factory = null;
        private static object objLock = new object();

        public static ISession GetSession()
        {
            if (_factory == null)
            {
                lock (objLock)
                {
                    if (_factory == null)
                        _factory = CreateSessionFactory();
                }
            }
            return _factory.OpenSession();
        }

        private static ISessionFactory CreateSessionFactory()
        {
            try
            {               
                var cfg = MySQLConfiguration.Standard.ConnectionString(c => 
                c.Is("server=localhost;user=arhiva_blanketa;charset=utf8;database=arhiva_blanketa;port=3306;password=starwars5;"));

                return Fluently.Configure()
                        .Database(cfg.ShowSql)
                        .Mappings(m => m.FluentMappings.AddFromAssemblyOf<MapBlanket>()
                        .Conventions.Add(FluentNHibernate.Conventions.Helpers.DefaultLazy.Never()))
                        .BuildSessionFactory();

            }
            catch (Exception ec)
            {
                Console.WriteLine(ec.ToString());

                return null;
            }

        }


    }
}
