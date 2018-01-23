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
    public class FakultetDataProvider
    {
        public IEnumerable<Fakultet> GetFakultet()
        {
            ISession s = DataLayer.GetSession();
            IEnumerable<Fakultet> fax = s.Query<Fakultet>().Select(r => r);
            return fax;
        }

        public Fakultet GetFakultet(int id)
        {
            ISession s = DataLayer.GetSession();
            return s.Query<Fakultet>().Where(r => r.IdFakultet == id).Select(p => p).FirstOrDefault();
        }

        public FakultetDTO GetFakultetDTO(int id)
        {
            ISession s = DataLayer.GetSession();
            Fakultet fax = s.Query<Fakultet>().Where(r => r.IdFakultet == id).Select(p => p).FirstOrDefault();

            if (fax == null)
            {
                return null;
            }

            return Mapper.Map<Fakultet, FakultetDTO>(fax);
        }

        public Fakultet AddFakultet(Fakultet g)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(g);
                s.Flush();
                Fakultet f = s.Query<Fakultet>().Where(c => c.Naziv == g.Naziv).FirstOrDefault();
                s.Close();
                return f;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Fakultet RemoveFakultet(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Fakultet g = s.Load<Fakultet>(id);
                s.Delete(g);
                s.Flush();
                s.Close();
                return GetFakultet(id);
            }
            catch (Exception ex)
            {
                return new Fakultet();
            }

        }

        public Fakultet UpdateFakultet(int id, Fakultet f)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Fakultet g = s.Load<Fakultet>(id);
                g.Grad = f.Grad;
                g.Naziv = f.Naziv;
                s.Update(g);
                s.Flush();
                s.Close();
                return GetFakultet(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}