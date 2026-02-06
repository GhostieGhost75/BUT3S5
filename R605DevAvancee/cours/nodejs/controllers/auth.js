exports.postAuth = (req,res,next) => {
    console.log('middleware auth', req.method);
    console.log(req.body.username);
    console.log(req.body.password);
    req.session.isLog=true;
    console.log(req.session.isLog)
    res.redirect('/');
}