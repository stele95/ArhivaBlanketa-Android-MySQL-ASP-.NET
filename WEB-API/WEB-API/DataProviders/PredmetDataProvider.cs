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
    public class PredmetDataProvider
    {

        public IEnumerable<Predmet> GetPredmet()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Predmet> p = s.Query<Predmet>().Select(r => r);

            return p;
        }

        public Predmet GetPredmet(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Predmet>().Where(r => r.IdPredmet == id).Select(p => p).FirstOrDefault();

        }

        public PredmetDTO GetPredmetDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Predmet d = s.Query<Predmet>().Where(r => r.IdPredmet == id).Select(p => p).FirstOrDefault();

            if (d == null)
            { return null; }


            return Mapper.Map<Predmet, PredmetDTO>(d);
        }

        public Predmet AddPredmet(Predmet r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(r);
                s.Flush();
                Predmet p = s.Query<Predmet>().Where(c => c.Naziv == r.Naziv).Where(c => c.Smer == r.Smer).Where(c => c.Godina == r.Godina).FirstOrDefault();
                s.Close();
                return p;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Predmet RemovePredmet(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Predmet r = s.Load<Predmet>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetPredmet(id);
            }
            catch (Exception ex)
            {
                return new Predmet();
            }
        }

        public Predmet UpdatePredmet(int id, Predmet p)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Predmet r = s.Load<Predmet>(id);
                r.Godina = p.Godina;
                r.Naziv = p.Naziv;
                r.Smer = p.Smer;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetPredmet(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}