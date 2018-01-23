using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Linq;
using NHibernate;
using WEB_API.Models;
using WEB_API.DTOs;

namespace WEB_API.DataProviders
{
    public class PrijavaDataProvider
    {
        public IEnumerable<Prijava> GetPrijava()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Prijava> k = s.Query<Prijava>().Select(r => r);

            return k;
        }
        
        public Prijava GetPrijava(int id)
        {
            ISession s = DataLayer.GetSession();

           return s.Query<Prijava>().Where(r => r.IdPrijava == id).Select(p => p).FirstOrDefault();
            
        }
        public Prijava AddPrijava(PrijavaDTO r)
        {
            try
            {             
                ISession s = DataLayer.GetSession();
                Prijava p = new Prijava();
                p.Blanket = s.Query<Blanket>().Where(x => x.IdBlanket == r.Blanket).FirstOrDefault();
                p.Dodao = s.Query<Korisnik>().Where(d => d.IdKorisnik == r.Dodao).FirstOrDefault();
                p.Datum = DateTime.Now;
                p.KomentarData = r.KomentarData;
                s.Save(p);
                s.Flush();
                Prijava f = s.Query<Prijava>().Where(c => c.Dodao == p.Dodao).Where(c => c.Datum == p.Datum)
                    .Where(c => c.KomentarData == p.KomentarData).Where(c => c.Blanket == p.Blanket).FirstOrDefault();
                s.Close();
                return f;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        
    }
}