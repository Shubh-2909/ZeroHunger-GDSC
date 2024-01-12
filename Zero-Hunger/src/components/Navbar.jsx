import asset from '../assets'
const Navbar = () => {
  return (
    <div className="h-16 px-10 py-1 flex gap-5" style={{backgroundColor:"#D9D9D9"}}>

      <div className='w-12 py-1'>
        <img src={asset.handshake} alt="Hunger" className='w-14'/>
      </div>

        <div className='w-46'>
          <p className='font-bold text-xl'>Waste less</p>
          <p className='font-extrabold text-xl'>Support More</p>
        </div>

        <div className='flex gap-40 ml-36 py-4 underline'>
          <a href="/">Home</a>
          <a href="/">About us</a>
          <a href="/">Role</a>
          <a href="/">Login</a>
        </div>

        <div className='ml-72 pl-5'>
          <p className='font-extrabold text-lg'>Do Some</p>
          <p className='font-extrabold text-lg'>Good Work</p>
        </div>
    </div>
  )
}

export default Navbar
