ALTER TABLE public.employees
    ADD FOREIGN KEY (leader) REFERENCES employees(id)