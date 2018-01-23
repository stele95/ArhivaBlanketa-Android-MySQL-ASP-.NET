using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEB_API.Models;

namespace WEB_API.DTOs
{
    public class SlusaDTO
    {
        public virtual SlusaID Id { get; set; }

        public SlusaDTO()
        {
            Id = new SlusaID();
        }
    }
}