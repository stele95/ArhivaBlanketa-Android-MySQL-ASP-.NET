using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Linq;
using NHibernate;
using WEB_API.Models;
using WEB_API.DTOs;
using AutoMapper;

namespace WEB_API.DataProviders
{
    public class GodinaStudijaDataProvider
    {
        public IEnumerable<GodinaStudija> GetGodinaStudija()
        {
            ISession s = DataLayer.GetSession();
            IEnumerable<GodinaStudija> godine = s.Query<GodinaStudija>().Select(r => r);
            return godine;
        }

        public GodinaStudija GetGodinaStudija(int id)
        {
            ISession s = DataLayer.GetSession();
            return s.Query<GodinaStudija>().Where(r => r.IdGodina == id).Select(p => p).FirstOrDefault();
        }

        public GodinaStudijaDTO GetGodinaStudijaDTO(int id)
        {
            ISession s = DataLayer.GetSession();
            GodinaStudija godine = s.Query<GodinaStudija>().Where(r => r.IdGodina == id).Select(p => p).FirstOrDefault();

            if (godine == null)
            {
                return null;
            }

            return Mapper.Map<GodinaStudija, GodinaStudijaDTO>(godine);
        }

        public GodinaStudija AddGodina(GodinaStudija g)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(g);
                s.Flush();
                GodinaStudija f = s.Query<GodinaStudija>().Where(c => c.Godina == g.Godina).FirstOrDefault();
                s.Close();
                return f;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public GodinaStudija RemoveGodinaStudija(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                GodinaStudija g = s.Load<GodinaStudija>(id);
                s.Delete(g);
                s.Flush();
                s.Close();
                return GetGodinaStudija(id);
            }
            catch (Exception ex)
            {
                return new GodinaStudija();
            }
        }

        public GodinaStudija UpdateGodinaStudija(int id, GodinaStudija gg)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                GodinaStudija g = s.Load<GodinaStudija>(id);
                g.Godina = gg.Godina;
                s.Update(g);
                s.Flush();
                s.Close();
                return GetGodinaStudija(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }

    }
}