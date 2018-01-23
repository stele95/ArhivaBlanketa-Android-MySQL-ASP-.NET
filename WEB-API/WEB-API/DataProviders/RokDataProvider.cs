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
    public class RokDataProvider
    {
        public IEnumerable<Rok> GetRok()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Rok> rokovi = s.Query<Rok>().Select(r=>r);
            
            return rokovi;
        }

        public Rok GetRok(int id)
        {
            ISession s = DataLayer.GetSession();
           
            return s.Query<Rok>().Where(r => r.IdRok == id).Select(p => p).FirstOrDefault();
           
        }

        public RokDTO GetRokDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Rok rok=s.Query<Rok>().Where(r => r.IdRok == id).Select(p => p).FirstOrDefault();
            
            if(rok==null)
            { return null; }

            
            return Mapper.Map<Rok, RokDTO>(rok);
        }

        public Rok AddRok(Rok r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(r);
                s.Flush();
                Rok rok = s.Query<Rok>().Where(c => c.Naziv == r.Naziv).FirstOrDefault();
                s.Close();
                return rok;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Rok RemoveRok(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Rok r = s.Load<Rok>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetRok(id);
            }
            catch (Exception ex)
            {
                return new Rok();
            }
        }

        public Rok UpdateRok(int id, Rok k)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Rok r = s.Load<Rok>(id);
                r.Naziv = k.Naziv;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetRok(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}